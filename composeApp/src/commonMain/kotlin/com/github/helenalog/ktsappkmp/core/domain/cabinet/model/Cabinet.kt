package com.github.helenalog.ktsappkmp.core.domain.cabinet.model

data class Cabinet(
    val id: String,
    val domain: String,
    val name: String,
    val createdBy: String? = null
)
