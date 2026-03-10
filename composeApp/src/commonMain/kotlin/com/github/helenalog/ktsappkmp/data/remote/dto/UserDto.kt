package com.github.helenalog.ktsappkmp.data.remote.dto

import com.github.helenalog.ktsappkmp.data.remote.dto.PhotoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("_id")
    val id: String,
    @SerialName("first_name")
    val firstName: String? = null,
    @SerialName("last_name")
    val lastName: String? = null,
    val username: String? = null,
    val photo: PhotoDto? = null,
) {
    val fullName: String
        get() = "${firstName.orEmpty()} ${lastName.orEmpty()}".trim()
}