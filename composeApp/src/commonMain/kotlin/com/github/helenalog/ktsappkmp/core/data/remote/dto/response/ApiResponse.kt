package com.github.helenalog.ktsappkmp.core.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val status: String,
    val data: T
)