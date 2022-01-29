package com.example.proyectoandroid._1data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "tb_carrito",
    foreignKeys = [
        ForeignKey(entity = ProductoEntity::class, parentColumns = ["id_prod"], childColumns = ["id_prod"]),
    ]
)
data class CarritoEntity(
    @PrimaryKey
    val id_carrito: String = "",
    @ColumnInfo(name = "cant")
    var cant: Int = 1,

    @ColumnInfo(name = "id_prod")
    val id_prod: Int = -1,
    @ColumnInfo(name = "nom_prod")
    val nom_prod: String = "",
    @ColumnInfo(name = "precio_final_prod")
    var precio_final_prod: Double = -1.0,
    @ColumnInfo(name = "img_prod")
    val img_prod: String = "",

    @ColumnInfo(name = "id_usuario")
    val id_usuario: String? = "",
)
