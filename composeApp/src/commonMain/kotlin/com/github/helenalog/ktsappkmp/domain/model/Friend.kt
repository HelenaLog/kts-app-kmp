package com.github.helenalog.ktsappkmp.domain.model

data class Friend(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val photo: String?,
    val isOnline: Boolean
) {
    val fullName: String
        get() = "$firstName $lastName"
}