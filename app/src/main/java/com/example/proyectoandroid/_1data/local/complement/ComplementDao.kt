package com.example.proyectoandroid._1data.local.complement

import androidx.room.*
import com.example.proyectoandroid._1data.model.ComplementosEntity

@Dao
interface ComplementDao {
    @Query("SELECT * FROM tb_complementos where id_categoria_complemento=:categCompleId")
    suspend fun getComplementByCategoryComplement(categCompleId: Int):List<ComplementosEntity>

    @Query("SELECT * FROM tb_complementos")
    suspend fun getComplementByCategoryComplement():List<ComplementosEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneComplement(complemento: ComplementosEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManyComplements(complementos: ArrayList<ComplementosEntity>)

    @Update
    suspend fun updateComplement(complemento: ComplementosEntity)
}
