package com.belyaninrom.network.model

sealed class NetworkResult<out T : Any> {
    class Success<out T: Any>(val data: T) : NetworkResult<T>()
    class Error (val code: Int, val message: String?) : NetworkResult<Nothing>()
    class Exception (val e: Throwable) : NetworkResult<Nothing>()
}