package com.example.proyectoandroid._1data.local.carrito


import androidx.room.*
import com.example.proyectoandroid._1data.model.CarritoComplementoEntity
import com.example.proyectoandroid._1data.model.CarritoEntity


@Dao
interface CarritoComplementoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComplementAssociatedToItemOfCarrito(carritoComplementoEntity: CarritoComplementoEntity)

    @Update
    suspend fun updateCarritoComplemento(carritoComplementoEntity: CarritoComplementoEntity)

    @Query("""
        SELECT 
        tb_carrito_complemento.id_carrito,
        tb_carrito_complemento.id_complemento,
        tb_carrito_complemento.nom_complemento,
        tb_carrito_complemento.precio_complemento,
        tb_carrito_complemento.cantidad
        FROM tb_carrito_complemento 
        INNER JOIN tb_carrito 
        ON tb_carrito.id_carrito = tb_carrito_complemento.id_carrito 
        WHERE tb_carrito.id_carrito = :idCarrito
        """)
    suspend fun getAllCarritoComplementByCarritoId(idCarrito: String?):List<CarritoComplementoEntity>

}