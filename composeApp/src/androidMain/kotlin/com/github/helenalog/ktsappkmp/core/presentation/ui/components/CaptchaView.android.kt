package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun CaptchaView(
    siteKey: String,
    onTokenReceive: (String) -> Unit,
    modifier: Modifier
) {
    val context = LocalContext.current

    val html = remember(siteKey) {
        context.assets.open("captcha.html")
            .bufferedReader()
            .readText()
            .replace("__SITE_KEY__", siteKey)
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            WebView(ctx).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webChromeClient = WebChromeClient()
                addJavascriptInterface(
                    object {
                        @JavascriptInterface
                        @Suppress("unused")
                        fun onTokenReceived(token: String) {
                            onTokenReceive(token)
                        }
                    },
                    "CaptchaBridge"
                )
                loadDataWithBaseURL(
                    "https://auth.smartbotpro.ru",
                    html,
                    "text/html",
                    "UTF-8",
                    null
                )
            }
        }
    )
}
