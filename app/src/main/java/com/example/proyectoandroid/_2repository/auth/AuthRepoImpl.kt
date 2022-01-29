package com.example.proyectoandroid._2repository.auth

import android.graphics.Bitmap
import com.example.proyectoandroid._1data.model.User
import com.example.proyectoandroid._1data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepoImpl(private val dataSource: AuthDataSource) : AuthRepo {
    override suspend fun signIn(email: String, password: String): FirebaseUser? {
        return dataSource.signIn(email, password)
    }

    override suspend fun signUp(password: String,nombre: String,apellido: String,telefono: String,email: String): FirebaseUser? {
        return dataSource.signUp(password, nombre,apellido,telefono,email)

    }
    override suspend fun updatePhotoProfile(imageBitmap: Bitmap) {
        dataSource.updatePhotoProfile(imageBitmap)
    }
    override suspend fun getUserProfile(): User {
        return dataSource.getUserProfile()
    }
    override suspend fun update_User_Profile(usuario:User) {
        return dataSource.update_User_Profile(usuario)
    }
}