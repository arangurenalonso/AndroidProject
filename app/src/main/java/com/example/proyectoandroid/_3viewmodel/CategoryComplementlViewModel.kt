package com.example.proyectoandroid._3viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoandroid._0core.Result
import com.example.proyectoandroid._2repository.categorycomplement.CategoryComplementRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception


class CategoryComplementlViewModel(
    private val repoCategCompl: CategoryComplementRepository
) : ViewModel() {


    fun fetchGetOneProductById() =
        liveData(Dispatchers.IO) {

            emit(Result.Loading())

            try {
                emit(Result.Success(repoCategCompl.getAllCategoriesComplement()))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }
}

class CategoryComplementllViewModelFactory(
    private val repoCategCompl: CategoryComplementRepository
):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CategoryComplementRepository::class.java).newInstance(repoCategCompl)
    }
}
