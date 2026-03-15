package com.github.helenalog.ktsappkmp.data.storage

object SessionProvider {
    lateinit var instance: SessionStorage
        private set

    fun init(storage: SessionStorage) {
        instance = storage
    }
}