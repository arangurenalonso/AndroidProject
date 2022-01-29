package com.example.proyectoandroid._1data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.proyectoandroid._1data.model.CategoriaComplementoEntity


@Entity(tableName = "tb_complementos",
    foreignKeys = [
        ForeignKey(entity = CategoriaComplementoEntity::class, parentColumns = ["id_categoria_complemento"], childColumns = ["id_categoria_complemento"]),
    ]
)
data class ComplementosEntity (
    @PrimaryKey
    val id_complemento: Int = -1,
    @ColumnInfo(name = "nom_complemento")
    val nom_complemento: String = "",
    @ColumnInfo(name = "precio_complemento")
    val precio_complemento: Double = -1.0,
    @ColumnInfo(name = "id_categoria_complemento")
    val id_categoria_complemento: Int = -1
)