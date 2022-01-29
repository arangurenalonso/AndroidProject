package com.example.proyectoandroid._2repository.auth

import android.graphics.Bitmap
import com.example.proyectoandroid._1data.model.User
import com.google.firebase.auth.FirebaseUser

interface AuthRepo {
    suspend fun signIn(email:String,password:String): FirebaseUser?
    suspend fun signUp(password: String,nombre: String,apellido: String,telefono: String,email: String): FirebaseUser?
    suspend fun updatePhotoProfile(imageBitmap: Bitmap)
    suspend fun getUserProfile(): User
    suspend fun update_User_Profile(usuario: User)
}