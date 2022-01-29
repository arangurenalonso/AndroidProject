package com.example.proyectoandroid._4view.producto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoandroid.R
import com.example.proyectoandroid.databinding.FragmentProductoBinding
import com.example.proyectoandroid._4view.producto.adapter.ProductoAdapter
import com.example.proyectoandroid._1data.AppDataBase
import com.example.proyectoandroid._1data.local.producto.LocalProductoSource
import com.example.proyectoandroid._1data.model.ProductoEntity
import com.example.proyectoandroid._2repository.product.ProductRepositoryImp
import com.example.proyectoandroid._3viewmodel.ProductViewModelFactory
import com.example.proyectoandroid._3viewmodel.ProductlViewModel
import com.example.proyectoandroid._0core.Result

class ProductoFragment : Fragment(), ProductoAdapter.AdaptadorProductoInterface {
    private lateinit var binding: FragmentProductoBinding
    private lateinit var productoAdapter: ProductoAdapter
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private val viewModel by viewModels<ProductlViewModel> {
        ProductViewModelFactory(
            ProductRepositoryImp(
                LocalProductoSource(AppDataBase.getInstanceDatabase(requireContext()).productDao()),
            )
        )
    }

    private val args by navArgs<ProductoFragmentArgs>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_producto, container, false)
    }

    override fun onButtomAddToCar(item: ProductoEntity) {
        //Toast.makeText(context,"Producto Seleccionada ${item.nom_prod}",Toast.LENGTH_SHORT).show()
        val action = ProductoFragmentDirections.actionProductoFragmentToDetailProductFragment(item.id_prod)
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentProductoBinding.bind(view)

        viewModel.fetchGetAllProductsByCategory(args.categoryId).observe(viewLifecycleOwner,{ result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    productoAdapter= ProductoAdapter(result.data,this)
                    mLinearLayoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL, false)

                    binding.rvProducto.apply {
                        layoutManager=mLinearLayoutManager
                        setHasFixedSize(true)
                        adapter=productoAdapter
                    }

                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context,"Error en la carga de datos", Toast.LENGTH_SHORT).show()

                }
            }
        })
    }
}