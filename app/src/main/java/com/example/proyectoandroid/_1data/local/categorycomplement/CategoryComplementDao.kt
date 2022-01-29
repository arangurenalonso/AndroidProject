package com.example.proyectoandroid._1data.local.categorycomplement


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyectoandroid._1data.model.CategoriaComplementoEntity


@Dao
interface CategoryComplementDao {
    @Query("SELECT * FROM tb_categoria_complemento")
    suspend fun getAllCategoriesComplement():List<CategoriaComplementoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneCategoryComplement(categoryComplement: CategoriaComplementoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManyCategorysComplements(categorysComplement: ArrayList<CategoriaComplementoEntity>)
}