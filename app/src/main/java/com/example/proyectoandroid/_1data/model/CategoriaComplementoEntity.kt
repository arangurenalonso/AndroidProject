package com.example.proyectoandroid._1data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_categoria_complemento")
data class CategoriaComplementoEntity(

    @PrimaryKey
    val id_categoria_complemento: Int = -1,
    @ColumnInfo(name = "nom_tipo_complemento")
    val nom_categ_complemento: String = "",
)