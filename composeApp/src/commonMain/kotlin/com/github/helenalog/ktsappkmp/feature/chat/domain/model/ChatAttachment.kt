package com.github.helenalog.ktsappkmp.feature.chat.domain.model

enum class ChatAttachmentType {
    IMAGE,
    FILE
}

data class ChatAttachment(
    val id: String,
    val type: ChatAttachmentType,
    val url: String? = null,
    val name: String? = null,
    val mimeType: String? = null,
    val size: Long? = null,
    val extension: String? = null
)
