package com.github.helenalog.ktsappkmp

@OptIn(kotlin.experimental.ExperimentalNativeApi::class)
class IOSPlatform : Platform {
    override val name: String = "iOS"
}

actual fun getPlatform(): Platform = IOSPlatform()
