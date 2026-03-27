package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttachmentUploadDto(
    @SerialName("_id")
    val id: String? = null,
    @SerialName("filename")
    val filename: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("preview_url")
    val previewUrl: String? = null,
    @SerialName("size")
    val size: Long? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("extension")
    val extension: String? = null
)