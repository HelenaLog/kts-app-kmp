package com.github.helenalog.ktsappkmp.data.storage

import android.content.Context

lateinit var appContext: Context

actual fun getFilesDir(): String = appContext.filesDir.absolutePath