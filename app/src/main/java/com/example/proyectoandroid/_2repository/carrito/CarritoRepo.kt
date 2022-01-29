package com.example.proyectoandroid._2repository.carrito

import android.graphics.Bitmap
import com.example.proyectoandroid._1data.model.*
import com.google.firebase.auth.FirebaseUser

interface CarritoRepo {
    suspend fun agregarCarrito(productoEntity: ProductoEntity,cantidad:Int,carritoComplementoList: List<CarritoComplementoEntity>)
    suspend fun getAllElementoCarritoByUser(): List<CarritoJoinComplemento>
    suspend fun getAllCarritoByUser(): List<CarritoEntity>
    suspend fun upcateCarrito(itemCarrito: CarritoEntity)
    suspend fun eliminarCarrito(itemCarrito: CarritoEntity)
}