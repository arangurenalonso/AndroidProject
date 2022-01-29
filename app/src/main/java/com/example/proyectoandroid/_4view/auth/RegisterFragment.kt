package com.example.proyectoandroid._4view.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.util.PatternsCompat.EMAIL_ADDRESS
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectoandroid.R
import com.example.proyectoandroid._0core.Result
import com.example.proyectoandroid._1data.remote.auth.AuthDataSource
import com.example.proyectoandroid._2repository.auth.AuthRepoImpl
import com.example.proyectoandroid._3viewmodel.auth.AuthViewModel
import com.example.proyectoandroid._3viewmodel.auth.AuthViewModelFactory
import com.example.proyectoandroid.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(
        AuthRepoImpl(
        AuthDataSource()
    )
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        signUp()
    }

    private fun signUp() {
        binding.btnSignup.setOnClickListener {

            val nombre = binding.editTextName.text.toString().trim()
            val apellido = binding.editTexApellido.text.toString().trim()
            val telefono = binding.editTextTelefono.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()

            if (validateCredentials(password, confirmPassword,nombre,apellido,telefono,email)) return@setOnClickListener

            createUser(password, nombre,apellido,telefono,email)


        }
    }

    private fun createUser(
        password: String,
        nombre: String,
        apellido: String,
        telefono: String,
        email: String
    ) {
        viewModel.signUp(password, nombre,apellido,telefono,email).observe(viewLifecycleOwner,  { result ->
            when(result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignup.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    //val user_key=FirebaseAuth.getInstance().currentUser?.uid
                    //Toast.makeText(requireContext(),"Welcome ${result.data?.email}", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_categoryFragment)

                }
                is Result.Failure -> {
                    binding.btnSignup.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun validateCredentials(
        password: String,
        confirmPassword: String,
        nombre: String,
        apellido: String,
        telefono: String,
        email: String
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
        if (!EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.error = "Ingrese un correo correcto"
            result=true
        }

        val passwordPattern = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}\$"
        if (!password.matches(Regex(passwordPattern))) {
            binding.editTextPassword.error = "La contraseña debe tener al entre 8 y 16 caracteres, al menos un dígito, al menos una minúscula y al menos una mayúscula."
            result=true
        }
        if (password != confirmPassword) {
            binding.editTextConfirmPassword.error = "Contraseña no coincide"
            result=true
        }


        return result
    }

}