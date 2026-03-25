package com.github.helenalog.ktsappkmp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExternalIdsDto(
    @SerialName("tg")
    val tg: TgExternalDto? = null
)