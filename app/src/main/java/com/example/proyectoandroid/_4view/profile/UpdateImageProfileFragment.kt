package com.example.proyectoandroid._4view.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectoandroid.R
import com.example.proyectoandroid._1data.remote.auth.AuthDataSource
import com.example.proyectoandroid._2repository.auth.AuthRepoImpl
import com.example.proyectoandroid._3viewmodel.auth.AuthViewModel
import com.example.proyectoandroid._3viewmodel.auth.AuthViewModelFactory
import com.example.proyectoandroid.databinding.FragmentSetupProfileBinding
import com.example.proyectoandroid._0core.Result

class UpdateImageProfileFragment : Fragment(R.layout.fragment_setup_profile) {

    private lateinit var binding: FragmentSetupProfileBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }
    private var bitmap: Bitmap? = null
    private val startForActivityTakePhoto=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if(result.resultCode==Activity.RESULT_OK){
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            binding.profileImage.setImageBitmap(imageBitmap)
            bitmap = imageBitmap

        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetupProfileBinding.bind(view)
        binding.profileImage.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startForActivityTakePhoto.launch(takePictureIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(requireContext(),"No se encontro app para abir la camara",Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCreateProfile.setOnClickListener {

            val alertDialog =AlertDialog.Builder(requireContext()).setTitle("Uploading photo...").create()
            bitmap?.let {

                    viewModel.updateUserProfile(imageBitmap = it)
                        .observe(viewLifecycleOwner, { result ->
                            when (result) {
                                is Result.Loading -> {
                                    alertDialog.show()
                                }
                                is Result.Success -> {
                                    alertDialog.dismiss()
                                    findNavController().navigate(R.id.profileFragment3)
                                }
                                is Result.Failure -> {
                                    alertDialog.dismiss()
                                }
                            }
                        })

            }
        }

    }



}