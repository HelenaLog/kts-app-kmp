package com.github.helenalog.ktsappkmp.domain.model

data class UserDto(
    val id: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val username: String? = null,
    val photo: PhotoDto? = null
) {
    val fullName: String
        get() = "${firstName.orEmpty()} ${lastName.orEmpty()}".trim()
}