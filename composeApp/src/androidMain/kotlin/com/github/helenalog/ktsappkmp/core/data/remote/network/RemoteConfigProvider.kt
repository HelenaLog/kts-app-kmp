package com.github.helenalog.ktsappkmp.core.data.remote.network

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import kotlinx.coroutines.tasks.await

actual class RemoteConfigProvider actual constructor() {
    private val remoteConfig = Firebase.remoteConfig

    actual suspend fun fetchAndActivate() {
        remoteConfig.fetchAndActivate().await()
    }

    actual fun getString(key: String): String {
        return remoteConfig.getString(key)
    }
}
