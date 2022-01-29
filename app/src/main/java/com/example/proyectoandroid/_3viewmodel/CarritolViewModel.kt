package com.example.proyectoandroid._3viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoandroid._0core.Result
import com.example.proyectoandroid._1data.model.CarritoComplementoEntity
import com.example.proyectoandroid._1data.model.CarritoEntity
import com.example.proyectoandroid._1data.model.CarritoJoinComplemento
import com.example.proyectoandroid._1data.model.ProductoEntity
import com.example.proyectoandroid._2repository.carrito.CarritoRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception


class CarritolViewModel(
    private val repoCarrito: CarritoRepo
) : ViewModel() {


    fun fetchInsertCarrito(
        productoEntity: ProductoEntity,
        cantidad: Int,
        carritoComplementoList: List<CarritoComplementoEntity>
    ) =
        liveData(Dispatchers.IO) {

            emit(Result.Loading())
            try {
                emit(
                    Result.Success(
                        repoCarrito.agregarCarrito(
                            productoEntity,
                            cantidad,
                            carritoComplementoList
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }

    fun fetchgetAllElementoCarritoByUser() =
        liveData(Dispatchers.IO) {

            emit(Result.Loading())
            try {

                emit(
                    Result.Success(
                        Pair(
                            repoCarrito.getAllCarritoByUser(),
                            repoCarrito.getAllElementoCarritoByUser()
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }
    fun fetchUpdateCarrito(itemCarrito: CarritoEntity) =
        liveData(Dispatchers.IO) {

            emit(Result.Loading())
            try {

                emit(
                    Result.Success(
                            repoCarrito.upcateCarrito(itemCarrito)
                    )
                )
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }
    fun fetchEliminarCarrito(itemCarrito: CarritoEntity) =
        liveData(Dispatchers.IO) {

            emit(Result.Loading())
            try {

                emit(
                    Result.Success(
                        repoCarrito.eliminarCarrito(itemCarrito)
                    )
                )
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }

}

class CarritolViewModelFactory(
    private val repoCarrito: CarritoRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CarritoRepo::class.java).newInstance(repoCarrito)
    }
}
