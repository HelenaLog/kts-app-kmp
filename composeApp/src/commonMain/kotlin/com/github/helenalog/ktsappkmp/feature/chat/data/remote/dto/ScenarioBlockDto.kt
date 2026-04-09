package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScenarioBlockDto(
    @SerialName("_id")
    val id: String,
    @SerialName("scenario_id")
    val scenarioId: String,
    @SerialName("name")
    val name: String,
    @SerialName("kind")
    val kind: String,
    @SerialName("params")
    val params: ScenarioBlockParamsDto? = null,
    @SerialName("type")
    val type: String
)

@Serializable
data class ScenarioBlockParamsDto(
    @SerialName("markup")
    val markup: String? = null,
    @SerialName("message_conditions")
    val messageConditions: List<MessageConditionDto>? = null
)

@Serializable
data class MessageConditionDto(
    @SerialName("key")
    val key: String? = null,
    @SerialName("value")
    val value: String? = null
)
