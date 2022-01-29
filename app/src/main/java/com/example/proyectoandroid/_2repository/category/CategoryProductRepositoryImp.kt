package com.example.proyectoandroid._2repository.category

import com.example.proyectoandroid._1data.local.categoryproduct.LocalCategoryProductoSource
import com.example.proyectoandroid._1data.model.CategoriaProductoEntity

class CategoryProductRepositoryImp(private val localCategoryProductoSource: LocalCategoryProductoSource):
    CategoryProductRepository {
    override suspend fun getAllCategories(): List<CategoriaProductoEntity> {
        return localCategoryProductoSource.getAllCategories()
    }

    override suspend fun insertManyCategorysProduct(categorysProduct: ArrayList<CategoriaProductoEntity>) {
        categorysProduct.forEach { category ->
            localCategoryProductoSource.insertCategorysProduct(category)
        }

    }
}