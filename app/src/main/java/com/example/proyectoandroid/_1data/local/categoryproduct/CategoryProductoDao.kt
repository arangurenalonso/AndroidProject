package com.example.proyectoandroid._1data.local.categoryproduct

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyectoandroid._1data.model.CategoriaProductoEntity

@Dao
interface CategoryProductoDao {

    @Query("SELECT * FROM tb_categoria_producto")
    suspend fun getAllCategories():List<CategoriaProductoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneCategoryProduct(categoryProduct: CategoriaProductoEntity)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
     fun insertManyCategorysProduct(categorysProduct: ArrayList<CategoriaProductoEntity>)
}