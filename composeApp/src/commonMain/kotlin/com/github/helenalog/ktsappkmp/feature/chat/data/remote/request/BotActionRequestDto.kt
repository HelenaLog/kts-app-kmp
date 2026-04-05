package com.github.helenalog.ktsappkmp.feature.chat.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BotActionRequestDto(
    @SerialName("conversation_id")
    val conversationId: Long,
    @SerialName("scenario_id")
    val scenarioId: String? = null,
    @SerialName("user_id")
    val userId: String? = null,
    @SerialName("block_id")
    val blockId: String? = null
)
