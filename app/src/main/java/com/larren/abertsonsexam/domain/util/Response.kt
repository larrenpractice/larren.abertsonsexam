package com.larren.abertsonsexam.domain.util

sealed class Response<out T> {

    data class Success<out T>(val value: T) : Response<T>()

    data class Failure(
        val statusCode: Int = 0,
        val errorTitle: String? = null,
        val errorMessage: String = "",
        val errorBody: String? = null,
        val throwable: Throwable? = null
    ) : Response<Nothing>()

    data class LocalNetworkError(
        val errorMessage: String = "",
        val throwable: Throwable? = null
    ) : Response<Nothing>()

    data class RemoteNetworkError(
        val errorMessage: String = "",
        val throwable: Throwable? = null
    ) : Response<Nothing>()

    data class UnknownError(
        val errorMessage: String = "",
        val throwable: Throwable? = null
    ) : Response<Nothing>()
}