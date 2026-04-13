package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

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
    val params: JsonElement? = null,
    @SerialName("type")
    val type: String
)

@Serializable
data class MessageConditionDto(
    @SerialName("key")
    val key: String? = null,
    @SerialName("value")
    val value: String? = null
)
