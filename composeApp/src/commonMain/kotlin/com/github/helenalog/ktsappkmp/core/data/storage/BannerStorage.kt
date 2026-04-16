package com.github.helenalog.ktsappkmp.core.data.storage

interface BannerStorage {
    suspend fun read(key: String): Set<String>
    suspend fun add(key: String, id: String)
}
