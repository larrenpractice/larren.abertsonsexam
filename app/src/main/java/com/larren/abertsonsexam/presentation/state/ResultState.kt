package com.larren.abertsonsexam.presentation.state

sealed class ResultState<out T>(
    val data: T? = null
) {

    class Success<out T>(data: T) : ResultState<T>(data)

    data class RemoteFailure(
        val throwable: Throwable? = null,
        val message: String? = "",
        val title: String? = "",
        val errorBody: String? = "",
        val statusCode: Int? = 0
    ) : ResultState<Nothing>()

    data class Loading(val isLoading: Boolean = true) : ResultState<Nothing>()

    class Default<T>(data: T? = null) : ResultState<T>(data)
}