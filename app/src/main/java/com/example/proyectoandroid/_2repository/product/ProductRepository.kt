package com.example.proyectoandroid._2repository.product

import com.example.proyectoandroid._1data.model.ProductoEntity

interface ProductRepository {

    suspend fun getAllProductsByCategory(idCateg:Int): List<ProductoEntity>
    suspend fun getOneProductById(idProd:Int): ProductoEntity
    suspend fun insertManyProducts(productos: ArrayList<ProductoEntity>)
}