package com.larren.abertsonsexam.domain.util

sealed class Response<out T> {

    data class Success<out T>(val value: T) : Response<T>()

    data class Failure(
        val errorMessage: String = "",
    ) : Response<Nothing>()
}