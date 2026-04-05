package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScenarioDto(
    @SerialName("_id")
    val id: String,
    @SerialName("name")
    val name: String
)
