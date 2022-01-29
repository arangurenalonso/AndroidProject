package com.example.proyectoandroid._2repository.categorycomplement

import com.example.proyectoandroid._1data.model.CategoriaComplementoEntity

interface CategoryComplementRepository {
    suspend fun getAllCategoriesComplement(): List<CategoriaComplementoEntity>

}