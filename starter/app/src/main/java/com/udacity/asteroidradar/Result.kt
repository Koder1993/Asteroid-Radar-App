package com.udacity.asteroidradar

sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val errorInfo: String? = null): Result<Nothing>()
}
