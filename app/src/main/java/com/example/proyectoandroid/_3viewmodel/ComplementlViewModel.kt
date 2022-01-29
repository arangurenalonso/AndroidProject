package com.example.proyectoandroid._3viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoandroid._0core.Quintuple
import com.example.proyectoandroid._0core.Result
import com.example.proyectoandroid._2repository.complement.ComplementRepository
import com.example.proyectoandroid._2repository.product.ProductRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception


class ComplementlViewModel(
    private val repoComp: ComplementRepository,
    private val repoProd: ProductRepository
) : ViewModel() {


    fun fetchGetALLComplementFilterByCategoryComplement(categId: Int) =
        liveData(Dispatchers.IO) {
            emit(Result.Loading())

            try {
                emit(Result.Success(repoComp.getComplementByCategoryComplement(categId)))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }

    fun fetchDetailProductScreamComplement(prodId:Int) =
        liveData(Dispatchers.IO) {
            emit(Result.Loading())

            try {

                emit(
                    Result.Success(
                        Quintuple(
                            repoProd.getOneProductById(prodId),
                            repoComp.getComplementByCategoryComplement(1),
                            repoComp.getComplementByCategoryComplement(2),
                            repoComp.getComplementByCategoryComplement(3),
                            repoComp.getComplementByCategoryComplement(4)
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }

}





class ComplementlViewModelFactory(
    private val repoComp: ComplementRepository,
    private val repoProd: ProductRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ComplementRepository::class.java, ProductRepository::class.java).newInstance(repoComp,repoProd)
    }
}
