package com.example.proyectoandroid._4view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectoandroid._1data.remote.auth.AuthDataSource
import com.example.proyectoandroid._2repository.auth.AuthRepoImpl
import com.example.proyectoandroid._3viewmodel.auth.AuthViewModel
import com.example.proyectoandroid._3viewmodel.auth.AuthViewModelFactory
import com.example.proyectoandroid.R
import com.example.proyectoandroid.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.example.proyectoandroid._0core.Result

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    /*
    * Forma 1 de inicializar una variable inmutable (by lazy)
    * La variable definida con by lazy se va a inicializar cuando se utiliza
    * */
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(
        AuthRepoImpl(AuthDataSource())
    ) }

    /*
    * Forma 2 de inicializar una variable
    * private lateinit var firebaseAuth2:FirebaseAuth
    * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        /*
        * Forma 2 de inicializar una variable
        * firebaseAuth2= FirebaseAuth.getInstance()
        * */
        isUserLoggedIn()
        doLogin()
        goToSignUpPage()
    }

    private fun isUserLoggedIn() {
        /*
        * firebaseAuth.currentUser
        * -> devuelve el usuario logeado en la aplicacion
        * Si el usuario tiene una cuenta logueada va al category fragment
        * */
        firebaseAuth.currentUser?.let { user ->
            findNavController().navigate(R.id.action_loginFragment_to_categoryFragment)
        }
    }
    private fun goToSignUpPage() {
        /*
        * Ir a la pagina registro
        * */
        binding.txtSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun doLogin() {
        binding.btnSignin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            validateCredentials(email, password)
            signIn(email,password)
        }
    }
    private fun validateCredentials(email: String, password: String) {
        if (email.isEmpty()) {
            binding.editTextEmail.error = "Correo es necesario"
            return
        }

        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password es necesario"
            return
        }
    }
    private fun signIn(email: String, password: String){
        viewModel.signIn(email,password).observe(viewLifecycleOwner,  { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignin.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),"Welcome ${result.data?.email}",Toast.LENGTH_SHORT).show()
                    if(result.data?.displayName.isNullOrEmpty()) {
                        findNavController().navigate(R.id.action_loginFragment_to_categoryFragment)
                    }else{
                        findNavController().navigate(R.id.action_loginFragment_to_categoryFragment)
                    }
                }
                is Result.Failure -> {
                    binding.btnSignin.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),"Error: ${result.exception}",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
