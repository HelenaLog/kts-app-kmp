package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttachmentDto(
    @SerialName("_id") val id: String? = null,
    val url: String? = null,
    @SerialName("preview_url") val previewUrl: String? = null,
    val filename: String? = null,
    val name: String? = null,
    val type: String? = null,
    val size: Long? = null,
)