package com.example.proyectoandroid._2repository.product

import com.example.proyectoandroid._1data.local.producto.LocalProductoSource
import com.example.proyectoandroid._1data.model.ProductoEntity

class ProductRepositoryImp (private val localProductoSource: LocalProductoSource):
    ProductRepository {
    override suspend fun getAllProductsByCategory(idCateg: Int): List<ProductoEntity> {
        return localProductoSource.getAllProductsByCategory(idCateg)
    }

    override suspend fun getOneProductById(idProd: Int): ProductoEntity {
        return localProductoSource.getOneProductById(idProd)
    }

    override suspend fun insertManyProducts(productos: ArrayList<ProductoEntity>) {
        productos.forEach {
            producto->
                localProductoSource.insertManyProducts(producto)
        }
    }
}