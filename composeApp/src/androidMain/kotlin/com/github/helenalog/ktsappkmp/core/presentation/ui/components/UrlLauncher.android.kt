package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

actual class UrlLauncher(private val context: Context) {
    actual fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}
