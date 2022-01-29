package com.example.proyectoandroid._4view.ordencompra

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.proyectoandroid.R
import com.example.proyectoandroid._0core.Result
import com.example.proyectoandroid._1data.AppDataBase
import com.example.proyectoandroid._1data.local.carrito.LocalCarritoSource
import com.example.proyectoandroid._1data.local.order.LocalOrderSource
import com.example.proyectoandroid._1data.model.CarritoEntity
import com.example.proyectoandroid._1data.model.User
import com.example.proyectoandroid._1data.remote.auth.AuthDataSource
import com.example.proyectoandroid._2repository.auth.AuthRepoImpl
import com.example.proyectoandroid._2repository.order.OrderRepoImpl
import com.example.proyectoandroid._3viewmodel.order.OrderViewModel
import com.example.proyectoandroid._3viewmodel.auth.AuthViewModel
import com.example.proyectoandroid._3viewmodel.auth.AuthViewModelFactory
import com.example.proyectoandroid._3viewmodel.order.OrderViewModelFactory
import com.example.proyectoandroid.databinding.FragmentOrdenBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class OrdenFragment : Fragment(R.layout.fragment_orden) {


    private lateinit var binding: FragmentOrdenBinding
    private lateinit var usuario: User
    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(
        AuthRepoImpl(AuthDataSource())
    ) }
    private val viewModelOrder by viewModels<OrderViewModel> {
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
    private val args by navArgs<OrdenFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrdenBinding.bind(view)
        set_user_profile()

    }
    private fun confirmDeleteDialog(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Desea procesar el pedido?")
            .setPositiveButton("Si", DialogInterface.OnClickListener{
                    dialogInterface, i ->

                generateOrder()
            })
            .setNegativeButton("Todavía", null)
            .show()
    }
    private fun generateOrder(){

        val direccion = binding.etNotaRestaurante.text.toString().trim()
        viewModelOrder.fetchGenerarOrder(args.montoTotalCarrito.toDouble(),usuario.direccion!!,direccion)
            .observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context,"Order Generada Satisfactoriamente", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.categoryFragment)
                    }
                    is Result.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context,"Error en la carga de datos", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }
    private fun set_user_profile(){
        viewModel.getUserProfile()
            .observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        usuario=result.data
                        isUserDirection()
                        setInformation()
                        setActions()

                    }
                    is Result.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context,"Error en la carga de datos", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }
    private fun setActions() {
        binding.btnGenerarOrden.setOnClickListener {
            confirmDeleteDialog()
        }
        binding.cvDireccion.setOnClickListener {
            goToDirection()
        }
    }
    private fun setInformation() {
        binding.tvDireccionUsuario.text=usuario.direccion
        binding.tvLatitud.text="Latitud:    ${String.format("%.2f", usuario.latitud)}"
        binding.tvLongitud.text="Longitud:  ${String.format("%.2f", usuario.longitud)}"
        binding.tvSubtotal.text="S/. ${args.montoTotalCarrito}"
        binding.tvTotal.text="S/. ${args.montoTotalCarrito}"
    }
    private fun goToDirection() {
        findNavController().navigate(R.id.action_ordenFragment_to_direccionFragment)
    }

    private fun isUserDirection() {
        if (usuario.direccion==null){
            Toast.makeText(context,"Es obligatorio ingresar una dirección", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_ordenFragment_to_direccionFragment)
        }


    }
}