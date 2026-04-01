package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendMessageAttachmentDto(
    @SerialName("_id")
    val id: String,
    @SerialName("as_document")
    val asDocument: Boolean = false,
)
