package com.github.helenalog.ktsappkmp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CabinetDto(
    @SerialName("_id")
    val id: String,
    @SerialName("domain")
    val domain: String,
    @SerialName("name")
    val name: String,
    @SerialName("created_by")
    val createdBy: String? = null
)