package com.example.proyectoandroid._4view.profile.pedidorealizado

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoandroid.R
import com.example.proyectoandroid._0core.Result
import com.example.proyectoandroid._1data.AppDataBase
import com.example.proyectoandroid._1data.local.carrito.LocalCarritoSource
import com.example.proyectoandroid._1data.local.order.LocalOrderSource
import com.example.proyectoandroid._1data.model.order.OrderEntity
import com.example.proyectoandroid._2repository.order.OrderRepoImpl
import com.example.proyectoandroid._3viewmodel.order.OrderViewModel
import com.example.proyectoandroid._3viewmodel.order.OrderViewModelFactory
import com.example.proyectoandroid._4view.profile.pedidorealizado.adapter.PedidoDetalleAdapter
import com.example.proyectoandroid.databinding.FragmentDetallePedidoBinding

class DetallePedidoFragment : Fragment(R.layout.fragment_detalle_pedido) {
    private lateinit var binding: FragmentDetallePedidoBinding
    private lateinit var pedidoDetalleAdapter: PedidoDetalleAdapter
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private val args by navArgs<DetallePedidoFragmentArgs>()


    private val viewModel by viewModels<OrderViewModel> {
        OrderViewModelFactory(
            OrderRepoImpl(
                LocalOrderSource(
                    AppDataBase.getInstanceDatabase(requireContext()).orderDao()
                ),
                LocalCarritoSource(
                    AppDataBase.getInstanceDatabase(requireContext()).carritoDao(),
                    AppDataBase.getInstanceDatabase(requireContext()).carritoComplementoDao()
                )
            )
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentDetallePedidoBinding.bind(view)
        cargarListaDetalleProducto()

    }
    private fun cargarListaDetalleProducto(){
        viewModel.fetchOrderDetail(args.orderId).observe(viewLifecycleOwner,{ result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    pedidoDetalleAdapter= PedidoDetalleAdapter(result.data.second,result.data.third)
                    mLinearLayoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL, false)
                    if(result.data.second.isEmpty()){
                        binding.constraintLayoutNoItem.visibility = View.VISIBLE
                    }
                    binding.rvDetallePedido.apply {
                        layoutManager=mLinearLayoutManager
                        setHasFixedSize(true)
                        adapter=pedidoDetalleAdapter
                    }

                    setInformation(result.data.first)

                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context,"Error en la carga de datos", Toast.LENGTH_SHORT).show()

                }
            }
        })

    }
    private fun setInformation(order: OrderEntity) {
        binding.tvSubtotal.text="S/. ${String.format("%.2f", order.total_orden)}"
        binding.tvTotal.text="S/. ${String.format("%.2f", order.total_orden)}"
    }

}