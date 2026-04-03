package com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatStateDto(
    @SerialName("stopped_by_manager")
    val stoppedByManager: Boolean = false,
    @SerialName("operator_tagged")
    val operatorTagged: Boolean = false,
    @SerialName("has_unanswered_operator_message")
    val hasUnansweredOperatorMessage: Boolean = false
)