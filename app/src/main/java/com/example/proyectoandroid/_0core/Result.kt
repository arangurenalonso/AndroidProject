package com.example.proyectoandroid._0core

import java.lang.Exception
/*
Permite setear los 3 estados
* */

sealed class Result <out T>{
    class Loading<out T>: Result<T>()

    data class Success<out T>(val data:T): Result<T>()

    data class Failure(val exception:Exception): Result<Nothing>()

}