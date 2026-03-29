package com.github.helenalog.ktsappkmp.feature.profile.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ManagerDto(
    @SerialName("_id")
    val id: String,
    @SerialName("email")
    val email: String,
    @SerialName("name")
    val name: String? = null,
    @SerialName("external_ids")
    val externalIds: ExternalIdsDto? = null
)
