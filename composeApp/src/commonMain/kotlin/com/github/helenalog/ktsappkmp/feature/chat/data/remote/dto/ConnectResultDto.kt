package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConnectResultDto(
    @SerialName("client")
    val client: String? = null,
    @SerialName("version")
    val version: String? = null,
    @SerialName("expires")
    val expires: Boolean = false,
    @SerialName("ttl")
    val ttl: Int? = null,
    @SerialName("ping")
    val ping: Int = 25,
    @SerialName("pong")
    val pong: Boolean = false
)