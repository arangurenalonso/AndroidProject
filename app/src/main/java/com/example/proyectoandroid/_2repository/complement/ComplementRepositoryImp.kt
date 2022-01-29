package com.example.proyectoandroid._2repository.complement

import com.example.proyectoandroid._1data.local.complement.LocalComplementSource
import com.example.proyectoandroid._1data.model.ComplementosEntity

class ComplementRepositoryImp(private val localComplementSource: LocalComplementSource):
    ComplementRepository {

    override suspend fun getComplementByCategoryComplement(idCateg: Int): List<ComplementosEntity> {
        return localComplementSource.getComplementByCategoryComplement(idCateg)
    }
}