package com.github.helenalog.ktsappkmp.core.data.storage

import platform.Foundation.NSUserDefaults

class SessionStorageImpl : SessionStorage {

    private val defaults = NSUserDefaults.standardUserDefaults

    override fun saveSession(cookie: String) {
        defaults.setObject(cookie, forKey = KEY_SESSION)
    }

    override fun getSession(): String? {
        return defaults.stringForKey(KEY_SESSION)
    }

    override fun clearSession() {
        defaults.removeObjectForKey(KEY_SESSION)
    }

    companion object {
        private const val KEY_SESSION = "spro_session"
    }
}
