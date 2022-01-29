package com.example.proyectoandroid._2repository.order

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.proyectoandroid._1data.local.carrito.LocalCarritoSource
import com.example.proyectoandroid._1data.local.order.LocalOrderSource
import com.example.proyectoandroid._1data.model.*
import com.example.proyectoandroid._1data.model.order.OrderDetailComplementoEntity
import com.example.proyectoandroid._1data.model.order.OrderDetailEntity
import com.example.proyectoandroid._1data.model.order.OrderEntity
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class OrderRepoImpl(
    private val localOrderSource: LocalOrderSource,
    private val localCarritoSource: LocalCarritoSource
) : OrderRepo {


    override suspend fun getAllOrdersByUser(): List<OrderEntity> {
        val user= FirebaseAuth.getInstance().currentUser
        return localOrderSource.getAllOrdersByUser(user?.uid!!)
    }

    override suspend fun getAllOrdersByID(orderID: String): OrderEntity {
        return localOrderSource.getAllOrdersByID(orderID)
    }

    override suspend fun getAllOrdersDetailByOrderID(orderID: String): List<OrderDetailEntity> {
        return localOrderSource.getAllOrdersDetailByOrderID(orderID)
    }

    override suspend fun getAllOrdersDetailComplementByOrderDetail(orderID: String): ArrayList<OrderDetailComplementoEntity> {

        val orderDetailComplemento =ArrayList<OrderDetailComplementoEntity>()

        localOrderSource.getAllOrdersDetailByOrderID(orderID).forEachIndexed {
                index, orderDetailEntity ->
            val listOrderDetailComplement=localOrderSource.getAllOrdersDetailComplementByOrderDetail(orderDetailEntity.id_order_producto)
            orderDetailComplemento.addAll(listOrderDetailComplement)
        }

        return orderDetailComplemento

    }

    override suspend fun generateOrder(
        monto_total_orden: Double,
        direccionUsu: String,
        comentarioPedido: String
    ) {
        val order_id = UUID.randomUUID().toString()
        val user = FirebaseAuth.getInstance().currentUser
        val order = OrderEntity(
            orderUUID = order_id,
            total_orden = monto_total_orden,
            id_usuario = user?.uid,
            direccion = direccionUsu,
            comentario = comentarioPedido,
            forma_pago = "efectivo",

            )
        val lista_carrito = localCarritoSource.getAllCarritoByUser(user?.uid)
        val detalleOrder =ArrayList<OrderDetailEntity>()
        val orderDetailComplemento =ArrayList<OrderDetailComplementoEntity>()

        lista_carrito.forEachIndexed {
                index, carritoEntity ->
            val orderDetailId = "orderDetailId_${UUID.randomUUID()}"

            val orderDetailEntity=OrderDetailEntity(
                id_order_producto = orderDetailId,
                cant= carritoEntity.cant,
                id_prod = carritoEntity.id_prod,
                nom_prod  = carritoEntity.nom_prod,
                precio_final_prod = carritoEntity.precio_final_prod,
                orderUUID = order_id
            )
            detalleOrder.add(orderDetailEntity)
            val lista_carrito_complemento =localCarritoSource.getAllCarritoComplementByCarritoId(carritoEntity.id_carrito)
            lista_carrito_complemento.forEachIndexed {
                    index, carritoComplementoEntity ->
                val orderDetailComplementId = "orderDetailComplementId_${UUID.randomUUID()}"
                val orderDetailComplementoEntity=OrderDetailComplementoEntity(
                    id_producto_complement= orderDetailComplementId,
                    id_order_producto = orderDetailId,
                    id_complemento=carritoComplementoEntity.id_complemento,
                    nom_complemento = carritoComplementoEntity.nom_complemento,
                    precio_complemento = carritoComplementoEntity.precio_complemento,
                    cantidad= carritoComplementoEntity.cantidad
                )
                orderDetailComplemento.add(orderDetailComplementoEntity)
            }
        }
        localOrderSource.generateOrder(order,detalleOrder,orderDetailComplemento,lista_carrito)
    }
}