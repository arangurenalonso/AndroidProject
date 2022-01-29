package com.example.proyectoandroid._1data.local.carrito

import com.example.proyectoandroid._1data.model.CarritoComplementoEntity
import com.example.proyectoandroid._1data.model.CarritoEntity
import com.example.proyectoandroid._1data.model.CarritoJoinComplemento


class LocalCarritoSource(private val carritoDao: CarritoDao, private val carritoComplementoDao: CarritoComplementoDao) {


    suspend fun getAllCarritoComplementByCarritoId(idCarrito: String?):List<CarritoComplementoEntity> {

        return carritoComplementoDao.getAllCarritoComplementByCarritoId(idCarrito)
    }
    suspend fun getAllCarritoByUser(user_uid: String?): List<CarritoEntity> {

        return carritoDao.getAllCarritoByUser(user_uid)
    }

    suspend fun deleteElementoCarrito(carritoEntity: CarritoEntity) {

        return carritoDao.deleteProductoAndComplementCarrito(carritoEntity)
    }
    suspend fun insertItemToCarrito(carritoEntity: CarritoEntity){

        return carritoDao.insertItemToCarrito(carritoEntity)
    }
    suspend fun insertComplementAssociatedToItemOfCarrito(carritoComplementoEntity: CarritoComplementoEntity) {

        return carritoComplementoDao.insertComplementAssociatedToItemOfCarrito(carritoComplementoEntity)
    }

    suspend fun upcateCarrito(itemCarrito: CarritoEntity) {
        carritoDao.updateElementocarrito(itemCarrito)
    }

    suspend fun getAllElementoCarritoByUser(user_uid: String?): List<CarritoJoinComplemento> {

        return carritoDao.getAllElementoCarritoByUser(user_uid)
    }
}