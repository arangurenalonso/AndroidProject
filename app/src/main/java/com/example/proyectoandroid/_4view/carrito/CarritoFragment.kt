package com.example.proyectoandroid._4view.carrito

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.proyectoandroid.R
import com.example.proyectoandroid._0core.Result
import com.example.proyectoandroid._1data.AppDataBase
import com.example.proyectoandroid._1data.local.carrito.LocalCarritoSource
import com.example.proyectoandroid._1data.model.CarritoComplementoEntity
import com.example.proyectoandroid._1data.model.CarritoEntity
import com.example.proyectoandroid._1data.model.CarritoJoinComplemento
import com.example.proyectoandroid._2repository.carrito.CarritoRepoImpl
import com.example.proyectoandroid._3viewmodel.CarritolViewModel
import com.example.proyectoandroid._3viewmodel.CarritolViewModelFactory
import com.example.proyectoandroid._4view.carrito.adapter.CarritoAdapter
import com.example.proyectoandroid._4view.categoria.CategoryFragmentDirections
import com.example.proyectoandroid._4view.categoria.adapter.CategoryAdapter
import com.example.proyectoandroid.databinding.CarritoItemBinding
import com.example.proyectoandroid.databinding.FragmentCarritoBinding
import com.example.proyectoandroid.databinding.FragmentCategoryBinding
import com.example.proyectoandroid.databinding.FragmentDetailProductBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class CarritoFragment : Fragment(R.layout.fragment_carrito),CarritoAdapter.AdaptadorCarritoInterface {
    private lateinit var binding: FragmentCarritoBinding
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private lateinit var carritoAdapter: CarritoAdapter
    private val viewModel by viewModels<CarritolViewModel> {
        CarritolViewModelFactory(
            CarritoRepoImpl(
                LocalCarritoSource(
                    AppDataBase.getInstanceDatabase(requireContext()).carritoDao(),
                    AppDataBase.getInstanceDatabase(requireContext()).carritoComplementoDao()
                )
            )
        )
    }

    private var listItemCarrito= mutableListOf<CarritoEntity>()
    private var listCarritoComplemento=mutableListOf<CarritoJoinComplemento>()
    private var costo_total_carrito=0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentCarritoBinding.bind(view)
        listar_carrito()
        binding.btnIrAPagar.setOnClickListener {
            val action = CarritoFragmentDirections.actionCarritoFragmentToOrdenFragment(
                costo_total_carrito.toFloat()
            )
            findNavController().navigate(action)
        }

    }


    override fun onClickAumentar(itemCarrito: CarritoEntity) {
        itemCarrito.cant ++
        update_carrito(itemCarrito)
    }
    override fun onClickDisminuir(itemCarrito: CarritoEntity) {
        if(itemCarrito.cant>1){
            itemCarrito.cant --
            update_carrito(itemCarrito)
        }else{
            confirmDeleteDialog(itemCarrito)
        }
    }
    private fun confirmDeleteDialog(itemCarrito: CarritoEntity){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Desea eliminar elemento del carrito?")
            .setPositiveButton("Eliminar", DialogInterface.OnClickListener{
                    dialogInterface, i ->

                eliminar_Carrito(itemCarrito)
            })
            .setNegativeButton("Cancel", null)
            .show()
    }
    private fun eliminar_Carrito(itemCarrito: CarritoEntity){
        viewModel.fetchEliminarCarrito(itemCarrito).observe(viewLifecycleOwner,{ result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    listar_carrito()
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context,"Error en la carga de datos", Toast.LENGTH_SHORT).show()

                }
            }
        })
    }
    private fun update_carrito(itemCarrito: CarritoEntity){
        viewModel.fetchUpdateCarrito(itemCarrito).observe(viewLifecycleOwner,{ result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    listar_carrito()
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context,"Error en la carga de datos", Toast.LENGTH_SHORT).show()

                }
            }
        })
    }
    private fun listar_carrito(){
        viewModel.fetchgetAllElementoCarritoByUser().observe(viewLifecycleOwner,{ result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    carritoAdapter= CarritoAdapter(result.data.first,result.data.second,this)
                    mLinearLayoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL, false)
                    listItemCarrito= result.data.first as MutableList<CarritoEntity>
                    listCarritoComplemento= result.data.second as MutableList<CarritoJoinComplemento>

                    if(listItemCarrito.size>0){
                        binding.constraintLayoutButon.visibility= View.VISIBLE
                    }else{
                        binding.constraintLayoutNoItem.visibility= View.VISIBLE
                    }

                    binding.rvCarrito.apply {
                        layoutManager=mLinearLayoutManager
                        setHasFixedSize(true)
                        adapter=carritoAdapter
                    }



                    calcular_total()

                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context,"Error en la carga de datos", Toast.LENGTH_SHORT).show()

                }
            }
        })
    }
    private fun calcular_total(){
        var costoTotal=0.0

        listItemCarrito.forEachIndexed { index, carritoEntity ->
            val cant_prod_comprar=carritoEntity.cant
            val costo_producto=carritoEntity.precio_final_prod

            val listCarritoComplementobyItem=listCarritoComplemento.filter { it.id_carrito==carritoEntity.id_carrito }
            var costo_complemento=listCarritoComplementobyItem.sumOf  { it.precio_complemento*it.cantidad }
            costoTotal+=(costo_producto+costo_complemento)*cant_prod_comprar
        }
        costo_total_carrito=costoTotal
        binding.btnIrAPagar.text="Ir a pagar  Subtotal....S/.${String.format("%.2f", costo_total_carrito).toDouble()}"

    }

}