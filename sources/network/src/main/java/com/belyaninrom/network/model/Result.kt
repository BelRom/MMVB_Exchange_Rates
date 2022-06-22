package com.belyaninrom.network.model

sealed class Result<out T : Any> {
    data class Success <out T : Any>(val value: T): Result<T>()
    data class Error (
        val throwable: Throwable?
    ): Result<Nothing>()
}
