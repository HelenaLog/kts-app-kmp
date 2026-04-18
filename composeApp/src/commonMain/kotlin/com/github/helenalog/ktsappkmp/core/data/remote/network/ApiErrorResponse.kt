package com.github.helenalog.ktsappkmp.core.data.remote.network

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    @SerialName("code")
    val code: String? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("message")
    val message: String? = null
)

suspend fun Throwable.asApiException(
    defaultMessage: String = "Произошла ошибка, попробуйте позже"
): Exception {
    return when (this) {
        is ClientRequestException,
        is ServerResponseException -> {
            val message = runCatching { response.body<ApiErrorResponse>().message }
                .getOrNull()
                ?: response.status.description
            Exception(message, this)
        }

        else -> Exception(message ?: defaultMessage, this)
    }
}

suspend fun <T> Result<T>.mapToApiError(
    defaultMessage: String = "Произошла ошибка, попробуйте позже"
): Result<T> =
    if (isSuccess) this else {
        val error = exceptionOrNull()
        if (error != null) Result.failure(error.asApiException(defaultMessage))
        else this
    }
