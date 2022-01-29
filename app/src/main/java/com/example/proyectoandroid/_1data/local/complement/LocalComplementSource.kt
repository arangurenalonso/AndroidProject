package com.example.proyectoandroid._1data.local.complement

import com.example.proyectoandroid._1data.model.ComplementosEntity

class LocalComplementSource(private val complementDao: ComplementDao) {
    suspend fun getComplementByCategoryComplement(idCategComple: Int): List<ComplementosEntity> {

        return complementDao.getComplementByCategoryComplement().filter { it.id_categoria_complemento==idCategComple }
    }
}