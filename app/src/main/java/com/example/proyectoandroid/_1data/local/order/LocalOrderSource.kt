package com.example.proyectoandroid._1data.local.order

import com.example.proyectoandroid._1data.model.CarritoComplementoEntity
import com.example.proyectoandroid._1data.model.CarritoEntity
import com.example.proyectoandroid._1data.model.CarritoJoinComplemento
import com.example.proyectoandroid._1data.model.ProductoEntity
import com.example.proyectoandroid._1data.model.order.OrderDetailComplementoEntity
import com.example.proyectoandroid._1data.model.order.OrderDetailEntity
import com.example.proyectoandroid._1data.model.order.OrderEntity


class LocalOrderSource(private val orderDao: OrderDao) {

    suspend fun getAllOrdersByUser(usuId: String): List<OrderEntity> {

        return orderDao.getAllOrders().filter { it.id_usuario== usuId}
    }

    suspend fun getAllOrdersByID(orderID: String): OrderEntity {

        return orderDao.getAllOrders().filter { it.orderUUID== orderID}.first()
    }
    suspend fun getAllOrdersDetailByOrderID(orderID: String): List<OrderDetailEntity> {

        return orderDao.getAllOrdersDetail().filter { it.orderUUID== orderID}
    }
    suspend fun getAllOrdersDetailComplementByOrderDetail(orderDetail: String): List<OrderDetailComplementoEntity> {

        return orderDao.getAllOrdersDetailComplement().filter { it.id_order_producto== orderDetail}
    }
    suspend fun generateOrder(
        order: OrderEntity,
        detalleOrder: ArrayList<OrderDetailEntity>,
        orderDetailComplemento: ArrayList<OrderDetailComplementoEntity>,
        listaCarrito:List<CarritoEntity>
    )  {
        orderDao.generateOrder(order,detalleOrder,orderDetailComplemento,listaCarrito)
    }
}