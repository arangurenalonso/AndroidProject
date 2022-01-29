package com.example.proyectoandroid._1data.model.order

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.proyectoandroid._1data.model.CarritoEntity
import com.example.proyectoandroid._1data.model.ComplementosEntity

@Entity(
    tableName = "tb_order_detail_complement",
    foreignKeys = [
        ForeignKey(
            entity = OrderDetailEntity::class,
            parentColumns = ["id_order_producto"],
            childColumns = ["id_order_producto"]
        ),
        ForeignKey(
            entity = ComplementosEntity::class,
            parentColumns = ["id_complemento"],
            childColumns = ["id_complemento"]
        ),
    ]
)
data class OrderDetailComplementoEntity(
    @PrimaryKey()
    val id_producto_complement: String = "",
    val id_order_producto: String = "",
    val id_complemento: Int=-1,
    @ColumnInfo(name = "nom_complemento")
    val nom_complemento: String = "",
    @ColumnInfo(name = "precio_complemento")
    val precio_complemento: Double = -1.0,
    @ColumnInfo(name = "cantidad")
    var cantidad: Int = 1
)



















