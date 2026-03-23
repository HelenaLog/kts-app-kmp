package com.github.helenalog.ktsappkmp.core.data.remote.network

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import com.github.helenalog.ktsappkmp.BuildKonfig
import com.github.helenalog.ktsappkmp.core.data.storage.SessionManager
import com.github.helenalog.ktsappkmp.core.data.storage.SessionProvider

object Networking {
    var onUnauthorized: (() -> Unit)? = null

    private val SPRO_URL = BuildKonfig.SPRO_URL
    private val AUTH_URL = BuildKonfig.BASE_URL
    private val CABINET_ID = BuildKonfig.CABINET_ID
    private val PROJECT_ID = BuildKonfig.PROJECT_ID

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.d(tag = "Ktor", message = message)
                }
            }
            level = LogLevel.ALL
        }
        defaultRequest {
            url(SPRO_URL)
            contentType(ContentType.Application.Json)
            header("X-SPro-Cabinet", CABINET_ID)
            header("X-SPro-Project", PROJECT_ID)
            SessionProvider.instance.getSession()?.let {
                header("Cookie", it)
            }
        }
        HttpResponseValidator {
            validateResponse { response ->
                if (response.status == HttpStatusCode.Unauthorized) {
                    SessionManager.logout()
                    onUnauthorized?.invoke()
                }
            }
        }
    }

    val authClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.d(tag = "Ktor", message = message)
                }
            }
            level = LogLevel.ALL
        }
        defaultRequest {
            url(AUTH_URL)
            contentType(ContentType.Application.Json)
        }
    }
}