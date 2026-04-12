package com.github.helenalog.ktsappkmp.core.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BannerDto(
    @SerialName("id")
    val id: String,
    @SerialName("type")
    val type: String,
    @SerialName("message")
    val message: String,
    @SerialName("actionText")
    val actionText: String? = null,
    @SerialName("actionUrl")
    val actionUrl: String? = null,
    @SerialName("enabled")
    val enabled: Boolean = true,
    @SerialName("dismissible")
    val dismissible: Boolean = true
)
