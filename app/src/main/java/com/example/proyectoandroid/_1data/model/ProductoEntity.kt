package com.example.proyectoandroid._1data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.proyectoandroid._1data.model.CategoriaProductoEntity

@Entity(tableName = "tb_productos",
        foreignKeys = [
                ForeignKey(entity = CategoriaProductoEntity::class, parentColumns = ["id_categ"], childColumns = ["id_categ"]),
        ]
)
data class ProductoEntity (
@PrimaryKey
val id_prod: Int = -1,
@ColumnInfo(name = "nom_prod")
val nom_prod: String = "",
@ColumnInfo(name = "descrip_prod")
val descrip_prod: String = "",
@ColumnInfo(name = "precio_inicial_prod")
val precio_inicial_prod: Double = -1.0,
@ColumnInfo(name = "stock_prod")
val stock_prod: Int = -1,
@ColumnInfo(name = "desc_prod")
val desc_prod: Double = -1.0,
@ColumnInfo(name = "precio_final_prod")
var precio_final_prod: Double = -1.0,
@ColumnInfo(name = "img_prod")
val img_prod: String = "",
@ColumnInfo(name = "id_categ")
val id_categ: Int = -1,
@ColumnInfo(name = "id_isActive")
val id_isActive: Int = -1,
        ){
        fun calcular_precio_final(){
                this.precio_final_prod=this.precio_inicial_prod*((100-this.desc_prod)/100)
        }
}
