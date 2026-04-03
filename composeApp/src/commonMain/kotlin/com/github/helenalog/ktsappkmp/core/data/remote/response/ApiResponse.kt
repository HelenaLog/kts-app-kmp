package com.github.helenalog.ktsappkmp.core.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    @SerialName("status")
    val status: String,
    @SerialName("data")
    val data: T
)
