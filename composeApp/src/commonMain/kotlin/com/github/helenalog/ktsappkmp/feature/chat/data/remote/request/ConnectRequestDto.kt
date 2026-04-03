package com.github.helenalog.ktsappkmp.feature.chat.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConnectRequestDto(
    @SerialName("token")
    val token: String
)