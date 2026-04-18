package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
    var isReady by remember { mutableStateOf(false) }

    val html = remember(siteKey) {
        context.assets.open("captcha.html")
            .bufferedReader()
            .readText()
            .replace("__SITE_KEY__", siteKey)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                WebView(ctx).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                    webChromeClient = WebChromeClient()
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView, url: String?) {
                            isReady = true
                        }
                    }
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

        if (!isReady) {
            CircularProgressIndicator()
        }
    }
}
