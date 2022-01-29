package com.example.proyectoandroid._1data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "tb_carrito_complemento",
    primaryKeys = ["id_carrito", "id_complemento"],
    foreignKeys = [
        ForeignKey(
            entity = CarritoEntity::class,
            parentColumns = ["id_carrito"],
            childColumns = ["id_carrito"]
        ),
        ForeignKey(
            entity = ComplementosEntity::class,
            parentColumns = ["id_complemento"],
            childColumns = ["id_complemento"]
        ),
    ]
)
data class CarritoComplementoEntity(
    var id_carrito: String="",
    val id_complemento: Int=-1,
    @ColumnInfo(name = "nom_complemento")
    val nom_complemento: String = "",
    @ColumnInfo(name = "precio_complemento")
    val precio_complemento: Double = -1.0,
    @ColumnInfo(name = "cantidad")
    var cantidad: Int = 1
)



















