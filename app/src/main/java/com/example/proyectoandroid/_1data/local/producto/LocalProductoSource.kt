package com.example.proyectoandroid._1data.local.producto

import com.example.proyectoandroid._1data.model.ProductoEntity

class LocalProductoSource(private val productoDao: ProductoDao) {
    suspend fun getAllProductsByCategory(idCateg: Int): List<ProductoEntity> {

        return productoDao.getAllProducts().filter { it.id_categ==idCateg }
    }
    suspend fun getOneProductById(idProd: Int): ProductoEntity {

        return productoDao.getAllProducts().filter { it.id_prod==idProd }.first()
    }
    suspend fun insertManyProducts(producto: ProductoEntity) {

        productoDao.insertOneProduct(producto)
    }
}