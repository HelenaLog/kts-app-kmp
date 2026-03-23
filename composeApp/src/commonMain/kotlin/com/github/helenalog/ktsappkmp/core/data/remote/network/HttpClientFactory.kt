package com.github.helenalog.ktsappkmp.core.data.remote.network

import com.github.helenalog.ktsappkmp.core.data.storage.SessionStorage
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
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

object HttpClientFactory {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun createAuthClient(config: NetworkConfig): HttpClient = HttpClient {
        installJson(json)
        installLogging()

        defaultRequest {
            url(config.authUrl)
            contentType(ContentType.Application.Json)
        }
    }

    fun createMainClient(
        config: NetworkConfig,
        sessionStorage: SessionStorage,
        onUnauthorizedCallback: OnUnauthorizedCallback,
    ): HttpClient = HttpClient {
        installJson(json)
        installLogging()

        defaultRequest {
            url(config.sproUrl)
            contentType(ContentType.Application.Json)
            header("X-SPro-Cabinet", config.cabinetId)
            header("X-SPro-Project", config.projectId)
            sessionStorage.getSession()?.let {
                header("Cookie", it)
            }
        }

        HttpResponseValidator {
            validateResponse { response ->
                if (response.status == HttpStatusCode.Unauthorized) {
                    onUnauthorizedCallback.invoke()
                }
            }
        }
    }
}

private fun HttpClientConfig<*>.installJson(json: Json) {
    install(ContentNegotiation) { json(json) }
}

private fun HttpClientConfig<*>.installLogging() {
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) = Napier.d(tag = "Ktor", message = message)
        }
        level = LogLevel.ALL
    }
}