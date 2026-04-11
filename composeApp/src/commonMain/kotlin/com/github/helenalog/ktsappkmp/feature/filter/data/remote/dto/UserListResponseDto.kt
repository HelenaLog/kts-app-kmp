package com.github.helenalog.ktsappkmp.feature.filter.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserListResponseDto(
    @SerialName("lists")
    val lists: List<ShortUserDto>
)
