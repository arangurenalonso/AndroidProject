package com.example.proyectoandroid._1data.local.order


import androidx.room.*
import com.example.proyectoandroid._1data.model.CarritoEntity
import com.example.proyectoandroid._1data.model.CarritoJoinComplemento
import android.provider.SyncStateContract.Helpers.insert
import com.example.proyectoandroid._1data.model.ProductoEntity
import com.example.proyectoandroid._1data.model.order.OrderDetailComplementoEntity
import com.example.proyectoandroid._1data.model.order.OrderDetailEntity
import com.example.proyectoandroid._1data.model.order.OrderEntity

@Dao
interface OrderDao {
    @Query("""
        SELECT 
        *       
        FROM tb_order  
        """)
    suspend fun getAllOrders():List<OrderEntity>

    @Query("""
        SELECT 
        *       
        FROM tb_order_detail  
        """)
    suspend fun getAllOrdersDetail():List<OrderDetailEntity>

    @Query("""
        SELECT 
        *       
        FROM tb_order_detail_complement  
        """)
    suspend fun getAllOrdersDetailComplement():List<OrderDetailComplementoEntity>
    @Transaction
    suspend fun generateOrder(
        order: OrderEntity,
        detalleOrder: ArrayList<OrderDetailEntity>,
        orderDetailComplementoEntity: ArrayList<OrderDetailComplementoEntity>,
        listaCarrito:List<CarritoEntity>
    ) {
        insertOrder(order)
        insertOrderDetail(detalleOrder)
        insertOrderDetailComplement(orderDetailComplementoEntity)
        listaCarrito.forEachIndexed {
                index, carritoEntity ->
            deleteCarritoComplement(carritoEntity.id_carrito)
            deleteElementoCarrito(carritoEntity)
        }

    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrderDetail(detalleOrder: ArrayList<OrderDetailEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrderDetailComplement(orderDetailComplementoEntity: ArrayList<OrderDetailComplementoEntity>)

    @Delete
    suspend fun deleteElementoCarrito(carritoEntity: CarritoEntity)

    @Query("""
        Delete 
        from tb_carrito_complemento
        WHERE tb_carrito_complemento.id_carrito = :idCarrito
        """)
    suspend fun deleteCarritoComplement(idCarrito: String?)


}