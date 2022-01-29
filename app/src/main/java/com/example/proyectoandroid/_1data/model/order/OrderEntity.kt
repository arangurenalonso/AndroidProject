package com.example.proyectoandroid._1data.model.order

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "tb_order")
data class OrderEntity (
    @PrimaryKey
    var orderUUID: String = "",
    @ColumnInfo(name = "total_orden")
    var total_orden: Double = 0.0,
    @ColumnInfo(name = "id_usuario")
    val id_usuario: String? = "",
    @ColumnInfo(name = "direccion")
    val direccion: String = "",
    @ColumnInfo(name = "comentario")
    val comentario: String = "",
    @ColumnInfo(name = "date_join")
    val date_join:String?= SimpleDateFormat("yyyy-MM-dd").format(Date()),
    @ColumnInfo(name = "forma_pago")
    val forma_pago:String?= "efectivo",

    )