package com.example.proyectoandroid._2repository.order

import android.graphics.Bitmap
import com.example.proyectoandroid._1data.model.*
import com.example.proyectoandroid._1data.model.order.OrderDetailComplementoEntity
import com.example.proyectoandroid._1data.model.order.OrderDetailEntity
import com.example.proyectoandroid._1data.model.order.OrderEntity
import com.google.firebase.auth.FirebaseUser
import java.util.ArrayList

interface OrderRepo {
    suspend fun generateOrder(monto_total_orden: Double,
                              direccionUsu: String,
                              comentarioPedido: String)

    suspend fun getAllOrdersByUser(): List<OrderEntity>

    suspend fun getAllOrdersByID(orderID: String): OrderEntity

    suspend fun getAllOrdersDetailByOrderID(orderID: String): List<OrderDetailEntity>

    suspend fun getAllOrdersDetailComplementByOrderDetail(orderID: String): ArrayList<OrderDetailComplementoEntity>
}