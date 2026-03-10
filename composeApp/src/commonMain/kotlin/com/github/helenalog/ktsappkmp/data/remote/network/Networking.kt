package com.github.helenalog.ktsappkmp.data.remote.network

import com.github.helenalog.ktsappkmp.data.storage.SessionStorage
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

object Networking {
    var onUnauthorized: (() -> Unit)? = null

    private val BASE_URL = BuildKonfig.BASE_URL
    private val CABINET_ID = BuildKonfig.CABINET_ID
    private val PROJECT_ID = BuildKonfig.PROJECT_ID
    private val SESSION = BuildKonfig.SESSION

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
            url(BASE_URL)
            contentType(ContentType.Application.Json)
            header("X-SPro-Cabinet", CABINET_ID)
            header("X-SPro-Project", PROJECT_ID)
            header("Cookie", "spro_session=$SESSION")
        }

        HttpResponseValidator {
            validateResponse { response ->
                if (response.status == HttpStatusCode.Unauthorized) {
                    onUnauthorized?.invoke()
                }
            }
        }
    }
}