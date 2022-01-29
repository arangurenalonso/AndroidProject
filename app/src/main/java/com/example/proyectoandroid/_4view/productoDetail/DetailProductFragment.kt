package com.example.proyectoandroid._4view.productoDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoandroid.R
import com.example.proyectoandroid.databinding.FragmentDetailProductBinding
import com.example.proyectoandroid._1data.AppDataBase
import com.example.proyectoandroid._1data.local.complement.LocalComplementSource
import com.example.proyectoandroid._1data.local.producto.LocalProductoSource
import com.example.proyectoandroid._1data.model.ComplementosEntity
import com.example.proyectoandroid._1data.model.ProductoEntity
import com.example.proyectoandroid._2repository.complement.ComplementRepositoryImp
import com.example.proyectoandroid._2repository.product.ProductRepositoryImp
import com.example.proyectoandroid._3viewmodel.ComplementlViewModel
import com.example.proyectoandroid._3viewmodel.CarritolViewModel
import com.example.proyectoandroid._3viewmodel.ComplementlViewModelFactory
import com.example.proyectoandroid._4view.productoDetail.Adapter.ComplementCheckAdapter
import com.example.proyectoandroid._4view.productoDetail.Adapter.DetailTitleAdapter
import com.example.proyectoandroid._4view.productoDetail.Adapter.concat.ComplementoConcatAdapter
import com.example.proyectoandroid._4view.productoDetail.Adapter.concat.CremaConcatAdapter
import com.example.proyectoandroid._4view.productoDetail.Adapter.concat.EnsaladaConcatAdapter
import com.example.proyectoandroid._4view.productoDetail.Adapter.concat.ExtraConcatAdapter
import com.example.proyectoandroid._0core.Result
import com.example.proyectoandroid._1data.local.carrito.LocalCarritoSource
import com.example.proyectoandroid._1data.model.CarritoComplementoEntity
import com.example.proyectoandroid._2repository.carrito.CarritoRepoImpl
import com.example.proyectoandroid._3viewmodel.CarritolViewModelFactory
import com.example.proyectoandroid._4view.productoDetail.Adapter.ComplementWithCantAdapter

class DetailProductFragment :
    Fragment(R.layout.fragment_detail_product),
    ComplementCheckAdapter.AdaptadorComplementInterface,
    ComplementWithCantAdapter.AdaptadorComplementwithCantInterface {

    private lateinit var binding: FragmentDetailProductBinding
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private lateinit var mConcatAdapter: ConcatAdapter

    private val args by navArgs<DetailProductFragmentArgs>()

    private var listComplementAdded = mutableListOf<CarritoComplementoEntity>()
    private var productSelect = ProductoEntity()
    private var coto_total_pagar=0.0
    private var cant_producto_Comprar=1

    private val viewModel by viewModels<ComplementlViewModel> {
        ComplementlViewModelFactory(
            ComplementRepositoryImp(
                LocalComplementSource(
                    AppDataBase.getInstanceDatabase(requireContext()).complementDao()
                ),
            ),

            ProductRepositoryImp(
                LocalProductoSource(AppDataBase.getInstanceDatabase(requireContext()).productDao()),
            )
        )
    }
    private val viewModelCarrito by viewModels<CarritolViewModel> {
        CarritolViewModelFactory(
            CarritoRepoImpl(
                LocalCarritoSource(
                    AppDataBase.getInstanceDatabase(requireContext()).carritoDao(),
                    AppDataBase.getInstanceDatabase(requireContext()).carritoComplementoDao()
                )
            )
        )
    }

    override fun onCheckCheckBox(item: ComplementosEntity) {
        val itemAdd = CarritoComplementoEntity(
            id_complemento = item.id_complemento,
            nom_complemento = item.nom_complemento,
            precio_complemento = item.precio_complemento,
            cantidad = 1
        )
        listComplementAdded.add(itemAdd)

        calcular_total()
    }

    override fun onUnCheckCheckBox(item: ComplementosEntity) {
        var itemAdd = CarritoComplementoEntity()
        listComplementAdded.forEachIndexed { index, carritoComplementoEntity ->
            if (item.id_complemento == carritoComplementoEntity.id_complemento) {
                itemAdd = carritoComplementoEntity
            }
        }
        listComplementAdded.remove(itemAdd)


    }

    override fun onBtnAdd(item: ComplementosEntity) {
        var indexIteam=-1
        var itemAdd = CarritoComplementoEntity()
        listComplementAdded.forEachIndexed { index, carritoComplementoEntity ->
            if (item.id_complemento == carritoComplementoEntity.id_complemento) {
                itemAdd = carritoComplementoEntity
                itemAdd.cantidad++
                indexIteam=index
            }
        }
        if(indexIteam==-1){
            itemAdd = CarritoComplementoEntity(
                id_complemento = item.id_complemento,
                nom_complemento = item.nom_complemento,
                precio_complemento = item.precio_complemento,
                cantidad = 1
            )
            listComplementAdded.add(itemAdd)
        }else{
            listComplementAdded.set(indexIteam,itemAdd)
        }
        calcular_total()
    }

    override fun onBtnDecrease(item: ComplementosEntity) {
        var indexIteam=-1
        var itemAdd = CarritoComplementoEntity()
        listComplementAdded.forEachIndexed { index, carritoComplementoEntity ->
            if (item.id_complemento == carritoComplementoEntity.id_complemento) {
                itemAdd = carritoComplementoEntity
                itemAdd.cantidad--
                indexIteam=index
            }
        }
        if(itemAdd.cantidad<=0){
            listComplementAdded.removeAt(indexIteam)
        }else{
            listComplementAdded.set(indexIteam,itemAdd)
        }
        calcular_total()
    }
    private fun calcular_total() {
        val precioProducto: Double = productSelect.precio_final_prod
        val precioTotalExtra: Double = listComplementAdded.sumOf { it.precio_complemento*it.cantidad }
        val cantComprar: Int = Integer.parseInt(binding.tvCantComprar.text.toString())
        coto_total_pagar = (precioTotalExtra + precioProducto) * cantComprar
        binding.btnAgregar.text = "Agregar S/. ${String.format("%.2f", coto_total_pagar).toDouble()}"
    }


    private fun agregarProductosCarrito(){
        viewModelCarrito.fetchInsertCarrito(productSelect,cant_producto_Comprar,listComplementAdded)
            .observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, "Producto ${productSelect.nom_prod} ha sido agregado correctamente", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.categoryFragment)

                    }
                    is Result.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Log.d("liveData", "${result.exception}")
                        Toast.makeText(context, "Error en la carga de datos", Toast.LENGTH_SHORT).show()
                    }

                }
            })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailProductBinding.bind(view)


        binding.btnAumentar.setOnClickListener {
            cant_producto_Comprar++
            binding.tvCantComprar.text = "${cant_producto_Comprar}"
            calcular_total()
        }

        binding.btnDisminuir.setOnClickListener {
            if (cant_producto_Comprar > 1) {
                cant_producto_Comprar--
                binding.tvCantComprar.text = "${cant_producto_Comprar}"
            }
            calcular_total()
        }

        binding.btnAgregar.setOnClickListener {
            agregarProductosCarrito()
        }



        mConcatAdapter = ConcatAdapter()
        viewModel.fetchDetailProductScreamComplement(args.prodId)
            .observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        Log.d("liveData", "Loading")
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        mConcatAdapter.apply {
                            addAdapter(
                                0,

                                DetailTitleAdapter(result.data.first)
                            )
                            addAdapter(
                                1,
                                ComplementoConcatAdapter(
                                    ComplementCheckAdapter(
                                        result.data.second,
                                        this@DetailProductFragment
                                    )
                                )
                            )
                            addAdapter(
                                2,
                                EnsaladaConcatAdapter(
                                    ComplementCheckAdapter(
                                        result.data.fifth,
                                        this@DetailProductFragment
                                    )
                                )
                            )
                            addAdapter(
                                3,
                                CremaConcatAdapter(
                                    ComplementCheckAdapter(
                                        result.data.fourth,
                                        this@DetailProductFragment
                                    )
                                )
                            )
                            addAdapter(
                                4,
                                ExtraConcatAdapter(
                                    ComplementWithCantAdapter(
                                        result.data.third,
                                        this@DetailProductFragment
                                    )
                                )
                            )

                            binding.btnAgregar.text =
                                "Agregar S/. ${result.data.first.precio_final_prod}"
                            productSelect = result.data.first
                        }
                        Log.d("liveData", "${mConcatAdapter.toString()}")

                        mLinearLayoutManager = LinearLayoutManager(
                            requireActivity(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )

                        binding.rvDetalleProducto.apply {
                            layoutManager = mLinearLayoutManager
                            setHasFixedSize(true)
                            adapter = mConcatAdapter
                        }

                    }
                    is Result.Failure -> {

                        binding.progressBar.visibility = View.GONE
                        Log.d("liveData", "${result.exception}")
                        Toast.makeText(context, "Error en la carga de datos", Toast.LENGTH_SHORT)
                            .show()

                    }

                }
            })
    }

}


