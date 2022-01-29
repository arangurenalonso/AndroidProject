package com.example.proyectoandroid._4view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.proyectoandroid.R
import com.example.proyectoandroid._0core.Result
import com.example.proyectoandroid._1data.remote.auth.AuthDataSource
import com.example.proyectoandroid._2repository.auth.AuthRepoImpl
import com.example.proyectoandroid._3viewmodel.auth.AuthViewModel
import com.example.proyectoandroid._3viewmodel.auth.AuthViewModelFactory
import com.example.proyectoandroid.databinding.FragmentLoginBinding
import com.example.proyectoandroid.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

// TOD
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(
        AuthRepoImpl(AuthDataSource())
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        isUserLoggedIn()
        set_Profile()
        setUpProfilePhoto()
        goToEditProfile()
        sign_out()
        goToGoogleMaps()
        goToPedidosRealizados()

    }

    private fun set_Profile(){
        viewModel.getUserProfile()
            .observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvName.text="!Hola ${result.data.nombre} ${result.data.apellido}¡"
                        Glide.with(requireContext())
                            .load(result.data.photo_url)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(binding.imageView)

                    }
                    is Result.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context,"Error en la carga de datos", Toast.LENGTH_SHORT).show()
                    }
                }
            })

    }

    private fun isUserLoggedIn() {
        if(firebaseAuth.currentUser==null){
            findNavController().navigate(R.id.loginFragment)
        }
    }
    private fun setUpProfilePhoto(){
        binding.imageView.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment3_to_setupProfileFragment)
        }
    }
    private fun goToEditProfile(){
        binding.tvDatosPersonales.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment3_to_updateProfileFragment)
        }
    }
    private fun goToGoogleMaps(){
        binding.tvDirecciones.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment3_to_direccionFragment)
        }
    }
    private fun goToPedidosRealizados(){
        binding.tvPedidosRecientes.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment3_to_pedidosRealizadoFragment)
        }
    }

    private fun sign_out(){
        binding.btnLogOut.setOnClickListener {

            firebaseAuth.signOut()
            Toast.makeText(requireContext(),"Hasta Pronto", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.loginFragment)
        }

    }

}