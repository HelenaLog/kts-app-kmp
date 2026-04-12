package com.github.helenalog.ktsappkmp.core.data.project.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectDto(
    @SerialName("_id")
    val id: String,
    @SerialName("name")
    val name: String,
)
