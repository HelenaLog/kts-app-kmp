package com.github.helenalog.ktsappkmp.core.data.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.core.content.edit


class SessionStorageImpl(private val context: Context) : SessionStorage {

    private val sharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun saveSession(cookie: String) {
        sharedPreferences.edit { putString(KEY_SESSION, cookie) }
    }

    override fun getSession(): String? {
        return sharedPreferences.getString(KEY_SESSION, null)
    }

    override fun clearSession() {
        sharedPreferences.edit { remove(KEY_SESSION) }
    }

    companion object {
        private const val KEY_SESSION = "spro_session"
    }
}
