package com.github.helenalog.ktsappkmp.core.data.remote.network

actual class RemoteConfigProvider actual constructor() {

    private val values: MutableMap<String, String> = mutableMapOf()

    actual suspend fun fetchAndActivate() {}

    actual fun getString(key: String): String {
        return values[key] ?: ""
    }
}
