package com.example.proyectoandroid._1data.model

import java.text.SimpleDateFormat
import java.util.*

data class User(
    var nombre: String? = "",
    var apellido: String? = "",
    var telefono: String? = "",
    val email: String? = "",
    val is_active:Boolean?=true,
    val user_type:Double?=1.0,
    val date_join:String?= SimpleDateFormat("yyyy-MM-dd").format(Date()),
    val photo_url: String? = "",
    var direccion:String?=null,
    var latitud:Double?=null,
    var longitud:Double?=null


)