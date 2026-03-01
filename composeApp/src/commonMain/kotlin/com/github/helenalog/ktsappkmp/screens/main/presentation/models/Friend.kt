package com.github.helenalog.ktsappkmp.screens.main.presentation.models

data class Friend(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val photo: String?,
    val online: Int?
)