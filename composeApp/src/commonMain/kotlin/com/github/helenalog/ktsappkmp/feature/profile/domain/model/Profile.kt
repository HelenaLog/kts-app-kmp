package com.github.helenalog.ktsappkmp.feature.profile.domain.model

data class Profile(
    val id: String,
    val email: String,
    val name: String,
    val avatarUrl: String? = null
)