package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScenarioBlockListDto(
    @SerialName("blocks")
    val blocks: List<ScenarioBlockDto>
)
