package com.example.proyectoandroid._1data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyectoandroid._1data.local.carrito.CarritoComplementoDao
import com.example.proyectoandroid._1data.local.carrito.CarritoDao
import com.example.proyectoandroid._1data.local.categorycomplement.CategoryComplementDao
import com.example.proyectoandroid._1data.local.categoryproduct.CategoryProductoDao
import com.example.proyectoandroid._1data.local.complement.ComplementDao
import com.example.proyectoandroid._1data.local.order.OrderDao
import com.example.proyectoandroid._1data.local.producto.ProductoDao
import com.example.proyectoandroid._1data.model.*
import com.example.proyectoandroid._1data.model.order.OrderDetailComplementoEntity
import com.example.proyectoandroid._1data.model.order.OrderDetailEntity
import com.example.proyectoandroid._1data.model.order.OrderEntity


@Database(
    entities = arrayOf(
        ProductoEntity::class,
        CategoriaProductoEntity::class,
        ComplementosEntity::class,
        CategoriaComplementoEntity::class,
        CarritoEntity::class,
        CarritoComplementoEntity::class,
        OrderEntity::class,
        OrderDetailEntity::class,
        OrderDetailComplementoEntity::class
    ),
    version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun productDao(): ProductoDao
    abstract fun categoryProductDao(): CategoryProductoDao
    abstract fun categoryComplementDao(): CategoryComplementDao
    abstract fun complementDao(): ComplementDao
    abstract fun carritoDao(): CarritoDao
    abstract fun carritoComplementoDao(): CarritoComplementoDao
    abstract fun orderDao():OrderDao

    companion object {
        private var INSTANCE: AppDataBase? = null
        fun getInstanceDatabase(context: Context): AppDataBase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java, "Hamburguesa"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return INSTANCE!!
        }

        fun destroyInstanceDatabase() {
            INSTANCE = null
        }

    }


}

