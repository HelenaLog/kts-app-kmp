package com.github.helenalog.ktsappkmp.core.data.storage

interface SessionStorage {
    fun saveSession(cookie: String)
    fun getSession(): String?
    fun clearSession()
}