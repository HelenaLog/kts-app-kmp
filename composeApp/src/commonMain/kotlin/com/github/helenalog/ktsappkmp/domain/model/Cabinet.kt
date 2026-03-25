package com.github.helenalog.ktsappkmp.domain.model

data class Cabinet(
    val id: String,
    val domain: String,
    val name: String,
    val createdBy: String? = null
)