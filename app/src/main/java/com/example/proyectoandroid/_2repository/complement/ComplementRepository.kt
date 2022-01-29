package com.example.proyectoandroid._2repository.complement

import com.example.proyectoandroid._1data.model.ComplementosEntity

interface ComplementRepository {

    suspend fun getComplementByCategoryComplement(idCateg:Int): List<ComplementosEntity>
}