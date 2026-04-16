package com.github.helenalog.ktsappkmp.core.data.remote.network

expect class RemoteConfigProvider() {
    suspend fun fetchAndActivate()
    fun getString(key: String): String
}
