package com.github.helenalog.ktsappkmp.domain.model

data class Friend(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val photo: String?,
    val online: Int?
)