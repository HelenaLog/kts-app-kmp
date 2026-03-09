package com.github.helenalog.ktsappkmp.domain.model

data class MessageDto(
    val id: String,
    val text: String? = null,
    val kind: MessageKind,
    val dateCreated: String? = null
)