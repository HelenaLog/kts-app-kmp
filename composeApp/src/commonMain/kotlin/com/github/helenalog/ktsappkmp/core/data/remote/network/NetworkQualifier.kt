package com.github.helenalog.ktsappkmp.core.data.remote.network

import org.koin.core.qualifier.named

object NetworkQualifier {
    val AUTH = named("auth")
    val MAIN = named("main")
    val WS = named("websocket")
}
