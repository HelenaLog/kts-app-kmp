package com.github.helenalog.ktsappkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
