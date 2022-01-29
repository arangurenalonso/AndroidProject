package com.example.proyectoandroid._2repository.category

import com.example.proyectoandroid._1data.model.CategoriaProductoEntity

interface CategoryProductRepository {
    suspend fun getAllCategories(): List<CategoriaProductoEntity>
    suspend fun insertManyCategorysProduct(categorysProduct: ArrayList<CategoriaProductoEntity>)

}