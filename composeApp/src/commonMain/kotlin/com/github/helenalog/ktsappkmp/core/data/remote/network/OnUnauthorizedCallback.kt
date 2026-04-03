package com.github.helenalog.ktsappkmp.core.data.remote.network

class OnUnauthorizedCallback {
    var onUnauthorized: (() -> Unit)? = null
    fun invoke() = onUnauthorized?.invoke()
}