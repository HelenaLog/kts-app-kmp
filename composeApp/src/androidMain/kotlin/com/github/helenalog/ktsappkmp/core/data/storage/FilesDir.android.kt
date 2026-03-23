package com.github.helenalog.ktsappkmp.core.data.storage

import android.content.Context

lateinit var appContext: Context

actual fun getFilesDir(): String = appContext.filesDir.absolutePath