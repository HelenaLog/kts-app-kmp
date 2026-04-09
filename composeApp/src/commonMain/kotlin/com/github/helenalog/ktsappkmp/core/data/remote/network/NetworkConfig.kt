package com.github.helenalog.ktsappkmp.core.data.remote.network

data class NetworkConfig(
    val sproUrl: String,
    val authUrl: String,
    val wsUrl: String,
    val cabinetId: String,
    val projectId: String,
    val bucket: String = "prod"
)
