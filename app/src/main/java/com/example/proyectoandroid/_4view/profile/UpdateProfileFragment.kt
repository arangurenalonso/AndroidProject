package com.example.proyectoandroid._4view.profile

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.proyectoandroid.R
import com.example.proyectoandroid._0core.Result
import com.example.proyectoandroid._1data.model.User
import com.example.proyectoandroid._1data.remote.auth.AuthDataSource
import com.example.proyectoandroid._2repository.auth.AuthRepoImpl
import com.example.proyectoandroid._3viewmodel.auth.AuthViewModel
import com.example.proyectoandroid._3viewmodel.auth.AuthViewModelFactory
import com.example.proyectoandroid.databinding.FragmentProfileBinding
import com.example.proyectoandroid.databinding.FragmentUpdateProfileBinding
import com.google.firebase.auth.FirebaseAuth

class UpdateProfileFragment : Fragment(R.layout.fragment_update_profile) {


    private lateinit var binding: FragmentUpdateProfileBinding
    private lateinit var usuario: User
    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(
        AuthRepoImpl(AuthDataSource())
    ) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpdateProfileBinding.bind(view)

        set_user_profile()
        actualizarDatos()

    }
    fun set_user_profile(){
        viewModel.getUserProfile()
            .observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        usuario=result.data
                        binding.editTextName.text= result.data.nombre?.editable()
                        binding.editTexApellido.text= result.data.apellido?.editable()
                        binding.editTextTelefono.text= result.data.telefono?.editable()
                    }
                    is Result.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context,"Error en la carga de datos", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }
    private fun actualizarDatos() {
        binding.btnSignup.setOnClickListener {

            val nombre = binding.editTextName.text.toString().trim()
            val apellido = binding.editTexApellido.text.toString().trim()
            val telefono = binding.editTextTelefono.text.toString().trim()
            if (validateCredentials(nombre,apellido,telefono)) return@setOnClickListener
            usuario.nombre=nombre
            usuario.apellido=apellido
            usuario.telefono=telefono

            update_User_Profile()
            findNavController().navigate(R.id.profileFragment3)
        }
    }
    private fun validateCredentials(
        nombre: String,
        apellido: String,
        telefono: String
    ): Boolean {

        var result:Boolean=false

        if (nombre.isEmpty()) {
            binding.editTextName.error = "Campo requerido"
            result=true
        }
        if (apellido.isEmpty()) {
            binding.editTexApellido.error = "Campo requerido"
            result=true
        }
        if (telefono.isEmpty()) {
            binding.editTexApellido.error = "Campo requerido"
            result=true
        }
        return result
    }

    fun update_User_Profile(){
        viewModel.update_User_Profile(usuario)
            .observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        set_user_profile()
                        Toast.makeText(context,"Usuario Actualizado", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context,"Error en la carga de datos", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    private fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

}