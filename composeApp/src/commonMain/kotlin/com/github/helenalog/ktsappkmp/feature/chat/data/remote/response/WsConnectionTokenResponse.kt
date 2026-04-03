package com.github.helenalog.ktsappkmp.feature.chat.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WsConnectionTokenResponse(
    @SerialName("auth_token")
    val authToken: String
)