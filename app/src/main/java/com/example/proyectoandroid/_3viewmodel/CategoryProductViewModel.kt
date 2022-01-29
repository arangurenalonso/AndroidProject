package com.example.proyectoandroid._3viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoandroid._0core.Result
import com.example.proyectoandroid._1data.model.CategoriaProductoEntity
import com.example.proyectoandroid._2repository.category.CategoryProductRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception


class CategoryProductViewModel(
    private val repoCategProd: CategoryProductRepository
) : ViewModel() {

    fun fetchGetAllCategories() = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(repoCategProd.getAllCategories()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun fetchInsertManyCategorysProduct(categorysProduct: ArrayList<CategoriaProductoEntity>) =
        liveData(Dispatchers.IO) {
            emit(Result.Loading())

            try {
                emit(Result.Success(repoCategProd.insertManyCategorysProduct(categorysProduct)))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }

}

class CategoryProductoViewModelFactory(
    private val repoCategProd: CategoryProductRepository
):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CategoryProductRepository::class.java).newInstance(repoCategProd)
    }
}
