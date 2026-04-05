package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpecialVarDto(
    @SerialName("key")
    val key: String,
    @SerialName("title")
    val title: String
)

@Serializable
data class SpecialVarListDto(
    @SerialName("vars")
    val vars: List<SpecialVarDto>
)
