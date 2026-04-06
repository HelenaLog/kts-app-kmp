package com.github.helenalog.ktsappkmp.feature.filter.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserListDto(
    @SerialName("_id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("tag")
    val tag: String,
    @SerialName("type")
    val type: String,
    @SerialName("count")
    val count: Int? = null
)
