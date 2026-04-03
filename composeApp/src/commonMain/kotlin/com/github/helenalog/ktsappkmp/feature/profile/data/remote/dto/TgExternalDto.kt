package com.github.helenalog.ktsappkmp.feature.profile.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TgExternalDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("username")
    val username: String? = null,
    @SerialName("first_name")
    val firstName: String? = null,
    @SerialName("last_name")
    val lastName: String? = null,
    @SerialName("link")
    val link: String? = null
)
