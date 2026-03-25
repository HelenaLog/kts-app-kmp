package com.github.helenalog.ktsappkmp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatStateDto(
    @SerialName("stopped_by_manager")
    val stoppedByManager: Boolean,
    @SerialName("operator_tagged")
    val operatorTagged: Boolean,
    @SerialName("has_unanswered_operator_message")
    val hasUnansweredOperatorMessage: Boolean
)