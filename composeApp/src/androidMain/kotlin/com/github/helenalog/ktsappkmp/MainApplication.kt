package com.github.helenalog.ktsappkmp

import android.app.Application
import com.github.helenalog.ktsappkmp.data.storage.SessionProvider
import com.github.helenalog.ktsappkmp.data.storage.SessionStorageImpl
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
        SessionProvider.init(SessionStorageImpl(this))
    }
}