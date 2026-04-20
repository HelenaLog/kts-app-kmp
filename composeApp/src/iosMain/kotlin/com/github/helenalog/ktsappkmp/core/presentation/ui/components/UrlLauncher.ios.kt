package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class UrlLauncher {
    actual fun openUrl(url: String) {
        val nsUrl = NSURL.URLWithString(url) ?: return
        UIApplication.sharedApplication.openURL(
            url = nsUrl,
            options = emptyMap<Any?, Any?>(),
            completionHandler = null
        )
    }
}
