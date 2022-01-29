package com.example.proyectoandroid._2repository.categorycomplement

import com.example.proyectoandroid._1data.local.categorycomplement.LocalCategoryComplementSource
import com.example.proyectoandroid._1data.model.CategoriaComplementoEntity
import com.example.proyectoandroid._2repository.categorycomplement.CategoryComplementRepository

class CategoryComplementRepositoryImp(private val localCategoryComplementSource: LocalCategoryComplementSource):
    CategoryComplementRepository {
    override suspend fun getAllCategoriesComplement(): List<CategoriaComplementoEntity> {
        return localCategoryComplementSource.getAllCategoriesComplement()
    }
}