package com.github.helenalog.ktsappkmp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExternalIdsDto(
    val tg: TgExternalDto? = null
)