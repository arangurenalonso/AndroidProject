package com.example.proyectoandroid._2repository.carrito

import androidx.room.ColumnInfo
import com.example.proyectoandroid._1data.local.carrito.LocalCarritoSource
import com.example.proyectoandroid._1data.model.*
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class CarritoRepoImpl(private val localCarritoSource: LocalCarritoSource) : CarritoRepo {
    override suspend fun agregarCarrito(
        productoEntity: ProductoEntity,
        cantidad: Int,
        carritoComplementoList: List<CarritoComplementoEntity>
    ){
        val carrito_iteam_id=UUID.randomUUID().toString()
        val user= FirebaseAuth.getInstance().currentUser
        val carrito_item=CarritoEntity(

            id_carrito =carrito_iteam_id,
            cant = cantidad,
            id_prod = productoEntity.id_prod,
            nom_prod = productoEntity.nom_prod,
            precio_final_prod = productoEntity.precio_final_prod,
            img_prod = productoEntity.img_prod,
            id_usuario = user?.uid
        )
        localCarritoSource.insertItemToCarrito(carrito_item)
        carritoComplementoList.forEachIndexed { index, carritoComplementoEntity ->
            carritoComplementoEntity.id_carrito=carrito_iteam_id
            localCarritoSource.insertComplementAssociatedToItemOfCarrito(carritoComplementoEntity)
        }
    }

    override suspend fun eliminarCarrito(
        itemCarrito: CarritoEntity
    ) {
        localCarritoSource.deleteElementoCarrito(itemCarrito)
    }

    override suspend fun upcateCarrito(itemCarrito: CarritoEntity) {
        localCarritoSource.upcateCarrito(itemCarrito)
    }

    override suspend fun getAllElementoCarritoByUser(): List<CarritoJoinComplemento> {
        val user= FirebaseAuth.getInstance().currentUser
        return localCarritoSource.getAllElementoCarritoByUser(user?.uid)
    }
    override suspend fun getAllCarritoByUser(): List<CarritoEntity> {
        val user= FirebaseAuth.getInstance().currentUser
        return localCarritoSource.getAllCarritoByUser(user?.uid)
    }
}