package com.example.proyectoandroid._1data.model

data class CarritoJoinComplemento (

    val id_carrito: String = "",
    val cant: Int = 1,
    val id_prod: Int = -1,
    val nom_prod: String = "",
    var precio_final_prod: Double = -1.0,
    val img_prod: String = "",
    val id_usuario: String = "",
    val id_complemento: Int=-1,
    val nom_complemento: String = "",
    val precio_complemento: Double = -1.0,
    var cantidad: Int = 1
)