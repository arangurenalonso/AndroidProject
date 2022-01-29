package com.example.proyectoandroid._1data.local.carrito


import androidx.room.*
import com.example.proyectoandroid._1data.model.CarritoEntity
import com.example.proyectoandroid._1data.model.CarritoJoinComplemento
import android.provider.SyncStateContract.Helpers.insert





@Dao
interface CarritoDao {

    @Query("""
        SELECT 
        tb_carrito.id_carrito,
        tb_carrito.cant,
        tb_carrito.id_prod,
        tb_carrito.nom_prod,
        tb_carrito.precio_final_prod,
        tb_carrito.img_prod,
        tb_carrito.id_usuario,
        tb_carrito_complemento.id_complemento,
        tb_carrito_complemento.nom_complemento,
        tb_carrito_complemento.precio_complemento,
        tb_carrito_complemento.cantidad                
        FROM tb_carrito INNER JOIN tb_carrito_complemento 
        ON tb_carrito.id_carrito = tb_carrito_complemento.id_carrito 
        WHERE tb_carrito.id_usuario = :idUsuario
        """)
    suspend fun getAllElementoCarritoByUser(idUsuario: String?):List<CarritoJoinComplemento>

    @Query("""
        SELECT 
        *       
        FROM tb_carrito  
        WHERE tb_carrito.id_usuario = :idUsuario
        """)
    suspend fun getAllCarritoByUser(idUsuario: String?):List<CarritoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemToCarrito(carritoEntity: CarritoEntity)

    @Update
    suspend fun updateElementocarrito(carritoEntity: CarritoEntity)


    @Transaction
    suspend fun deleteProductoAndComplementCarrito(carritoEntity: CarritoEntity) {
        deleteCarritoComplement(carritoEntity.id_carrito)
        deleteElementoCarrito(carritoEntity)
    }

    @Delete
    suspend fun deleteElementoCarrito(carritoEntity: CarritoEntity)

    @Query("""
        Delete 
        from tb_carrito_complemento
        WHERE tb_carrito_complemento.id_carrito = :idCarrito
        """)
    suspend fun deleteCarritoComplement(idCarrito: String?)


}