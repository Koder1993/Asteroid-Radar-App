package com.udacity.asteroidradar

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> safeFunctionCall(dispatcher: CoroutineDispatcher, function: suspend () -> T): Result<T> {
    return withContext(dispatcher) {
        try {
            Result.Success(function.invoke())
        } catch (throwable: Throwable) {
            Result.Error(throwable.localizedMessage)
        }
    }
}