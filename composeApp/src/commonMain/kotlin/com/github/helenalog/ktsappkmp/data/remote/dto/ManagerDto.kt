package com.github.helenalog.ktsappkmp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ManagerDto(
    @SerialName("_id")
    val id: String,
    val email: String,
    val name: String? = null,
    @SerialName("external_ids")
    val externalIds: ExternalIdsDto? = null
)