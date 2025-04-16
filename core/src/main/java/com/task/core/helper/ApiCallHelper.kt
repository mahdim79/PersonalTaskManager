package com.task.core.helper

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ApiCallHelper {
    companion object {
        private const val BAD_REQUEST = 400
        private const val UNAUTHORIZED = 401
        private const val FORBIDDEN = 403
        private const val NOT_FOUND = 404
        private const val NOT_ALLOWED = 405
        private const val INTERNAL_SERVER_ERROR = 500

        suspend fun <T> safeApiCall(apiCall: suspend () -> T): Flow<DataState<T>> = flow {
            emit(
                try {
                    val a = apiCall.invoke()
                    DataState.Success(a)
                } catch (throwable: Throwable) {
                    when (throwable) {
                        is HttpException -> DataState.NetworkError(extractHttpExceptions(throwable))
                        is IOException -> DataState.NetworkError(
                            NetworkErrorType.NetworkConnection(
                                throwable.message
                            )
                        )

                        is TimeoutCancellationException -> DataState.NetworkError(
                            NetworkErrorType.NetworkConnection(
                                throwable.message
                            )
                        )

                        else -> DataState.NetworkError(NetworkErrorType.Unknown(if (!throwable.message.isNullOrEmpty()) throwable.message else null))
                    }
                }
            )
        }

        private fun extractHttpExceptions(ex: HttpException): NetworkErrorType {
            return try {
                when (ex.code()) {
                    UNAUTHORIZED ->
                        NetworkErrorType.UnAuthorized(message = ex.message)

                    FORBIDDEN ->
                        NetworkErrorType.Forbidden(message = ex.message())

                    NOT_ALLOWED ->
                        NetworkErrorType.NotAllowed(message = ex.message)

                    INTERNAL_SERVER_ERROR ->
                        NetworkErrorType.InternalServerError(message = ex.message)

                    BAD_REQUEST ->
                        NetworkErrorType.BadRequest(ex.message)

                    NOT_FOUND ->
                        NetworkErrorType.ResourceNotFound(message = ex.message)

                    else -> NetworkErrorType.Unknown(message = ex.message)

                }
            } catch (exception: Exception) {
                NetworkErrorType.Unknown()
            }
        }
    }
}