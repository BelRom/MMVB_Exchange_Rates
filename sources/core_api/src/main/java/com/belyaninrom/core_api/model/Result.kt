package com.belyaninrom.core_api.model

sealed class Result<out T : Any> {
    object StartLoading: Result<Nothing>()
    data class Success <out T : Any>(val value: T): Result<T>()
    data class Error (
        val throwable: Throwable?
    ): Result<Nothing>()
}
