package com.example.proyectoandroid._3viewmodel.auth

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.proyectoandroid._2repository.auth.AuthRepo
import com.example.proyectoandroid._0core.Result
import com.example.proyectoandroid._1data.model.User
import kotlinx.coroutines.Dispatchers

class AuthViewModel(private val repo: AuthRepo) : ViewModel() {

    fun signIn(email: String, password: String) =
        liveData(Dispatchers.IO) {

            emit(Result.Loading())
            try {
                emit(Result.Success(repo.signIn(email, password)))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }
    fun getUserProfile() =
        liveData(Dispatchers.IO) {

            emit(Result.Loading())
            try {
                emit(Result.Success(repo.getUserProfile()))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }
    fun update_User_Profile(ususario:User) =
        liveData(Dispatchers.IO) {

            emit(Result.Loading())
            try {
                emit(Result.Success(repo.update_User_Profile(ususario)))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }
    fun signUp(password: String,nombre: String,apellido: String,telefono: String,email: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.signUp(password, nombre, apellido, telefono, email)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun updateUserProfile(imageBitmap: Bitmap) =
        liveData(Dispatchers.IO) {
            emit(Result.Loading())
            try {
                emit(Result.Success(repo.updatePhotoProfile(imageBitmap)))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }

}

/*
* Necesario para inyectar el repo
* */
class AuthViewModelFactory(private val repo: AuthRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AuthRepo::class.java).newInstance(repo)
    }
}