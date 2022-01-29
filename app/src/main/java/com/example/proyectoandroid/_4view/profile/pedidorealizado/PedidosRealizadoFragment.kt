package com.example.proyectoandroid._4view.profile.pedidorealizado

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
import com.example.proyectoandroid._4view.profile.pedidorealizado.adapter.PedidoAdapter
import com.example.proyectoandroid.databinding.FragmentPedidosRealizadoBinding


class PedidosRealizadoFragment : Fragment(R.layout.fragment_pedidos_realizado), PedidoAdapter.AdaptadorOrderInterface {

    private lateinit var binding: FragmentPedidosRealizadoBinding
    private lateinit var pedidoRealizadoAdapter: PedidoAdapter
    private lateinit var mLinearLayoutManager: LinearLayoutManager

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

    override fun onOrderClick(orderEntity: OrderEntity) {
        val action = PedidosRealizadoFragmentDirections.actionPedidosRealizadoFragmentToDetallePedidoFragment(orderEntity.orderUUID)
        findNavController().navigate(action)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentPedidosRealizadoBinding.bind(view)
        cargarListaPedidoRealizado()

    }
    private fun cargarListaPedidoRealizado(){
        viewModel.fetchgetAllOrdersByUser().observe(viewLifecycleOwner,{ result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    pedidoRealizadoAdapter= PedidoAdapter(result.data,this)
                    mLinearLayoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL, false)
                    if(result.data.size>0){
                        binding.tvTitulo.visibility = View.VISIBLE
                    }else{
                        binding.constraintLayoutNoItem.visibility = View.VISIBLE
                    }
                    binding.rvPedidosRecientes.apply {
                        layoutManager=mLinearLayoutManager
                        setHasFixedSize(true)
                        adapter=pedidoRealizadoAdapter
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