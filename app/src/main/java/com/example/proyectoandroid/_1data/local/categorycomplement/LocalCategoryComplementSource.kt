package com.example.proyectoandroid._1data.local.categorycomplement

import com.example.proyectoandroid._1data.model.CategoriaComplementoEntity


class LocalCategoryComplementSource(private val categoryComplementDao: CategoryComplementDao) {

    suspend fun getAllCategoriesComplement(): List<CategoriaComplementoEntity> {

        return categoryComplementDao.getAllCategoriesComplement()
    }

}