package com.example.proyectoandroid._1data.local.categoryproduct

import com.example.proyectoandroid._1data.model.CategoriaProductoEntity


class LocalCategoryProductoSource(private val categoryProductoDao: CategoryProductoDao) {

    suspend fun getAllCategories(): List<CategoriaProductoEntity> {

        return categoryProductoDao.getAllCategories()
    }
    suspend fun insertCategorysProduct(categorysProduct: CategoriaProductoEntity) {

        categoryProductoDao.insertOneCategoryProduct(categorysProduct)
    }

}