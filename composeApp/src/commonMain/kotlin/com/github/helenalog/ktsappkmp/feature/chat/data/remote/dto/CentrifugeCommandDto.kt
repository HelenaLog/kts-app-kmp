package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import com.github.helenalog.ktsappkmp.feature.chat.data.remote.request.ConnectRequestDto
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.request.SubscribeRequestDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CentrifugeCommandDto(
    @SerialName("id")
    val id: Int,
    @SerialName("connect")
    val connect: ConnectRequestDto? = null,
    @SerialName("subscribe")
    val subscribe: SubscribeRequestDto? = null
)