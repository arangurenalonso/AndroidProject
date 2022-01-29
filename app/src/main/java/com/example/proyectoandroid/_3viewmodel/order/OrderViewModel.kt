package com.example.proyectoandroid._3viewmodel.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoandroid._0core.Result
import com.example.proyectoandroid._1data.model.CarritoComplementoEntity
import com.example.proyectoandroid._1data.model.CarritoEntity
import com.example.proyectoandroid._1data.model.CarritoJoinComplemento
import com.example.proyectoandroid._1data.model.ProductoEntity
import com.example.proyectoandroid._2repository.carrito.CarritoRepo
import com.example.proyectoandroid._2repository.order.OrderRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception


class OrderViewModel(
    private val repoOrder: OrderRepo
) : ViewModel() {


    fun fetchGenerarOrder(
        monto_total_orden: Double,
        direccionUsu: String,
        comentarioPedido: String
    ) =
        liveData(Dispatchers.IO) {

            emit(Result.Loading())
            try {
                emit(
                    Result.Success(
                        repoOrder.generateOrder(
                            monto_total_orden,
                            direccionUsu,
                            comentarioPedido
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }

    fun fetchgetAllOrdersByUser() =
        liveData(Dispatchers.IO) {

            emit(Result.Loading())
            try {

                emit(
                    Result.Success(
                            repoOrder.getAllOrdersByUser()
                    )
                )
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }


    fun fetchOrderDetail(orderID: String) =
        liveData(Dispatchers.IO) {

            emit(Result.Loading())
            try {

                emit(
                    Result.Success(
                        Triple(
                            repoOrder.getAllOrdersByID(orderID),
                            repoOrder.getAllOrdersDetailByOrderID(orderID),
                            repoOrder.getAllOrdersDetailComplementByOrderDetail(orderID)
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }
}

class OrderViewModelFactory(
    private val repoOrder: OrderRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(OrderRepo::class.java).newInstance(repoOrder)
    }
}
