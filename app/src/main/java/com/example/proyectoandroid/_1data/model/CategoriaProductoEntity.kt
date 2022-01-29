package com.example.proyectoandroid._1data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_categoria_producto")
data class CategoriaProductoEntity (
    @PrimaryKey
    val id_categ: Int = -1,
    @ColumnInfo(name = "nombre_categ")
    val nombre_categ: String = "",
    @ColumnInfo(name = "img_categ")
    val img_categ: String = "" )