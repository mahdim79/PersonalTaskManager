package com.task.core.helper

sealed class DataState<out T> {
    data object Loading: DataState<Nothing>()
    data class Success<out T>(val value: T): DataState<T>()
    data class LocalError(val message: String? = null): DataState<Nothing>()
    data class NetworkError(val networkError: NetworkErrorType?): DataState<Nothing>()
    data object NoInternet: DataState<Nothing>()
}