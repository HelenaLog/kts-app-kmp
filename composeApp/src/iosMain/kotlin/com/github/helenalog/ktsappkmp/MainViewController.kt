package com.github.helenalog.ktsappkmp

import androidx.compose.ui.window.ComposeUIViewController
import com.github.helenalog.ktsappkmp.core.di.appModules
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController { App() }

fun initKoin() {
    Napier.base(DebugAntilog())
    startKoin {
        modules(appModules())
    }
}
