package com.github.helenalog.ktsappkmp

import android.app.Application
import com.github.helenalog.ktsappkmp.core.di.appModules
import com.github.helenalog.ktsappkmp.core.logging.TracerAntilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.ok.tracer.CoreTracerConfiguration
import ru.ok.tracer.HasTracerConfiguration
import ru.ok.tracer.TracerConfiguration
import ru.ok.tracer.crash.report.CrashFreeConfiguration
import ru.ok.tracer.crash.report.CrashReportConfiguration

class MainApplication : Application(), HasTracerConfiguration {

    override val tracerConfiguration: List<TracerConfiguration>
        get() = listOf(
            CoreTracerConfiguration.build {
                setDebugUpload(true)
            },
            CrashReportConfiguration.build {},
            CrashFreeConfiguration.build {}
        )

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Napier.base(DebugAntilog())
        }
        Napier.base(TracerAntilog())
        startKoin {
            androidContext(this@MainApplication)
            modules(appModules())
        }
    }
}
