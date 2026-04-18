package com.github.helenalog.ktsappkmp.core.utils

import android.app.Application
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.io.Source
import kotlinx.io.asSource
import kotlinx.io.buffered
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private object AppContextHolder : KoinComponent {
    val app: Application by inject()
}

actual fun PlatformFile.toSource(): Source {
    val context = AppContextHolder.app
    val stream = context.contentResolver.openInputStream(uri)
        ?: error("Невозможно открыть поток для файла: $name")
    return stream.asSource().buffered()
}
