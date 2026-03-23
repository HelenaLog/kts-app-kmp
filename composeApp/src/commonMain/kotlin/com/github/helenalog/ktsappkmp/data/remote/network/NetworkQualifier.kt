package com.github.helenalog.ktsappkmp.data.remote.network

import org.koin.core.qualifier.named

object NetworkQualifier {
    val AUTH = named("auth")
    val MAIN = named("main")
}