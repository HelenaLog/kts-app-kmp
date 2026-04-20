package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cValue
import platform.CoreGraphics.CGRect
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile
import platform.WebKit.WKScriptMessage
import platform.WebKit.WKScriptMessageHandlerProtocol
import platform.WebKit.WKUserContentController
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.darwin.NSObject

private const val HANDLER_NAME = "CaptchaBridge"

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun CaptchaView(
    siteKey: String,
    onTokenReceive: (String) -> Unit,
    modifier: Modifier
) {
    val tokenCallback = remember(onTokenReceive) { onTokenReceive }

    UIKitView(
        modifier = modifier,
        factory = {
            val configuration = WKWebViewConfiguration()
            val contentController = WKUserContentController()

            val handler = object : NSObject(), WKScriptMessageHandlerProtocol {
                override fun userContentController(
                    userContentController: WKUserContentController,
                    didReceiveScriptMessage: WKScriptMessage
                ) {
                    val token = didReceiveScriptMessage.body as? String ?: return
                    tokenCallback(token)
                }
            }
            contentController.addScriptMessageHandler(handler, name = HANDLER_NAME)
            configuration.userContentController = contentController

            val webView = WKWebView(frame = cValue<CGRect>(), configuration = configuration)

            val htmlPath = NSBundle.mainBundle.pathForResource("captcha", ofType = "html")
            if (htmlPath != null) {
                @Suppress("UNCHECKED_CAST")
                val rawHtml = NSString.stringWithContentsOfFile(
                    htmlPath,
                    encoding = NSUTF8StringEncoding,
                    error = null
                ) as? String
                if (rawHtml != null) {
                    val html = rawHtml.replace("__SITE_KEY__", siteKey)
                    val baseUrl = NSURL.URLWithString("https://auth.smartbotpro.ru")
                    webView.loadHTMLString(html, baseURL = baseUrl)
                }
            }

            webView
        }
    )
}
