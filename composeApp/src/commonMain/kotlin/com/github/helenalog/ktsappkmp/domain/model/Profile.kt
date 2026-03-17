package com.github.helenalog.ktsappkmp.domain.model

data class Profile(
    val id: String,
    val email: String,
    val name: String,
    val avatarUrl: String? = null
)