package com.example.proyectoandroid._4view.profile

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectoandroid.R
import com.example.proyectoandroid._0core.Result
import com.example.proyectoandroid._1data.model.User
import com.example.proyectoandroid._1data.remote.auth.AuthDataSource
import com.example.proyectoandroid._2repository.auth.AuthRepoImpl
import com.example.proyectoandroid._3viewmodel.auth.AuthViewModel
import com.example.proyectoandroid._3viewmodel.auth.AuthViewModelFactory
import com.example.proyectoandroid.databinding.FragmentDireccionBinding
import com.example.proyectoandroid.databinding.FragmentProfileBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception
import java.util.*

class DireccionFragment : Fragment(R.layout.fragment_direccion), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener , GoogleMap.OnMyLocationClickListener{


    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentDireccionBinding

    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(
        AuthRepoImpl(AuthDataSource())
    ) }
    private lateinit var usuario: User


    private var ultimoMarcador: Marker?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDireccionBinding.bind(view)
        set_user_profile()


        binding.btnProcesarDireccion.setOnClickListener{

            update_User_Profile()
            findNavController().navigate(R.id.profileFragment3)
        }

    }
    private fun createMapFragment() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            }
            else -> {
            Toast.makeText(requireContext(), "Necesita Otrgar permiso para acceder al modulo direcciones", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.profileFragment3)
        }
        }
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            map = googleMap
        }


        if (ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))
                findNavController().navigate(R.id.profileFragment3)
                return
            }
        map.isMyLocationEnabled=true
        map.uiSettings.isZoomControlsEnabled=true
        map.uiSettings.isCompassEnabled=true

        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            if (location != null) {
                var ubicacion:LatLng
                if(usuario.direccion==null){
                    ubicacion = LatLng(location.latitude,location.longitude)
                }else{
                    ubicacion = LatLng(usuario.latitud!!,usuario.longitud!!)
                    val markerOptions=MarkerOptions().position(ubicacion)
                    map.addMarker(markerOptions)
                }
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 18f),100,null)

                val nombre_direccion=obtenerDireccion(ubicacion)
                Toast.makeText(requireContext(), " ${nombre_direccion}", Toast.LENGTH_SHORT).show()


            }

        }
            map.setOnMyLocationButtonClickListener(this)
            map.setOnMyLocationClickListener (this)

        map.setOnMapClickListener { location->
            val markerOptions=MarkerOptions().position(location)
            //Cambiar  el boton del marcador
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))

            val nombredireccion=obtenerDireccion(location)
            markerOptions.title(nombredireccion)

            if(ultimoMarcador!=null){
                ultimoMarcador!!.remove()
            }

            ultimoMarcador= map.addMarker(markerOptions)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 18f),100,null)
        }

    }
    fun obtenerDireccion(location:LatLng):String{



        val geocoder=Geocoder(requireContext(),Locale.getDefault())
        val direcciones: List<Address>?
        val primeraDireccion:Address
        var textoDireccion =""

        try {

            direcciones=geocoder.getFromLocation(location.latitude,location.longitude,1)

            if(direcciones!=null && direcciones.isNotEmpty()){
                primeraDireccion=direcciones[0]
                if (primeraDireccion.maxAddressLineIndex>0){
                    for(i in 0 .. primeraDireccion.maxAddressLineIndex){
                        textoDireccion+=primeraDireccion.getAddressLine(i) + "\n"
                    }
                }else{
                    textoDireccion+=primeraDireccion.thoroughfare + "," + primeraDireccion.subThoroughfare
                }
            }
        }catch (e:Exception){

            textoDireccion=" Direccion no encontrada"
        }
        usuario.direccion=textoDireccion
        usuario.latitud=location.latitude
        usuario.longitud=location.longitude

        binding.nombreDireccion.text=textoDireccion

        return textoDireccion
    }
    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(requireContext(), "Boton pulsado", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(requireContext(), "Estás en ${p0.latitude}, ${p0.longitude}", Toast.LENGTH_SHORT).show()
    }


    override fun onResume() {
        super.onResume()
        if (!::map.isInitialized) return
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
                        createMapFragment()
                        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
                    }
                    is Result.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context,"Error en la carga de datos", Toast.LENGTH_SHORT).show()
                    }
                }
            })
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
                        Toast.makeText(context,"Dirección Actualizada", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context,"Error en la carga de datos", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }
}