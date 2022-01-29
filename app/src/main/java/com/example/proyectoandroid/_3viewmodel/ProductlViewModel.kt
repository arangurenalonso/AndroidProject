package com.example.proyectoandroid._3viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoandroid._0core.Result
import com.example.proyectoandroid._1data.model.ProductoEntity
import com.example.proyectoandroid._2repository.product.ProductRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception


class ProductlViewModel(
    private val repoProd: ProductRepository
) : ViewModel() {


    fun fetchGetOneProductById(prodId:Int) =
        liveData(Dispatchers.IO) {

            emit(Result.Loading())
            try {
                emit(Result.Success(repoProd.getOneProductById(prodId)))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }
    fun fetchGetAllProductsByCategory(categId:Int) =
        liveData(Dispatchers.IO) {
            emit(Result.Loading())

            try {
                emit(Result.Success(repoProd.getAllProductsByCategory(categId)))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }
    fun fetchInsertManyProducts(productos: ArrayList<ProductoEntity>) =
        liveData(Dispatchers.IO) {
            emit(Result.Loading())

            try {
                emit(Result.Success(repoProd.insertManyProducts(productos)))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }
}

class ProductViewModelFactory(
    private val repoProd: ProductRepository
):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ProductRepository::class.java).newInstance(repoProd)
    }
}
