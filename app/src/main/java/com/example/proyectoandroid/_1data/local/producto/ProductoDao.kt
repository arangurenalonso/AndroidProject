package com.example.proyectoandroid._1data.local.producto

import androidx.room.*
import com.example.proyectoandroid._1data.model.ProductoEntity

@Dao
interface ProductoDao {
    @Query("SELECT * FROM tb_productos")
    suspend fun getAllProducts():List<ProductoEntity>

    @Query("SELECT * FROM tb_productos WHERE id_prod = :prodId")
    suspend fun getOneProductById(prodId: Int): ProductoEntity

    @Query("SELECT * FROM tb_productos WHERE id_categ = :categId")
    suspend fun getAllProductsByCategory(categId: Int): List<ProductoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneProduct(producto: ProductoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManyProducts(productos: ArrayList<ProductoEntity>)

    @Update
    fun update(producto: ProductoEntity)
}
