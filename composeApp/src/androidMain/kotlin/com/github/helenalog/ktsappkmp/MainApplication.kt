package com.github.helenalog.ktsappkmp

import android.app.Application
import com.github.helenalog.ktsappkmp.core.di.appModules
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
        startKoin {
            androidContext(this@MainApplication)
            modules(appModules())
        }
    }
}