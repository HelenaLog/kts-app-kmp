package com.github.helenalog.ktsappkmp.data.storage

interface SessionStorage {
    fun saveSession(cookie: String)
    fun getSession(): String?
    fun clearSession()
}