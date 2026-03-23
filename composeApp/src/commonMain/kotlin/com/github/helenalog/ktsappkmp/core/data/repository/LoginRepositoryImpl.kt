package com.github.helenalog.ktsappkmp.core.data.repository

import com.github.helenalog.ktsappkmp.core.data.remote.api.LoginApi
import com.github.helenalog.ktsappkmp.core.data.remote.network.Networking
import com.github.helenalog.ktsappkmp.core.data.storage.SessionProvider
import com.github.helenalog.ktsappkmp.domain.repository.LoginRepository
import kotlin.coroutines.cancellation.CancellationException

class LoginRepositoryImpl : LoginRepository {
    private val api = LoginApi(Networking.authClient)
    private val sessionStorage = SessionProvider.instance

    override suspend fun login(
        username: String,
        password: String,
        captchaToken: String
    ): Result<Unit> = runCatching {
        val response = api.login(
            email = username,
            password = password,
            captchaToken = captchaToken
        )
        val cookie = response.headers[HEADER_SET_COOKIE]
            ?: error(ERROR_NO_COOKIE)
        val sessionValue = cookie
            .split(COOKIE_SEPARATOR)
            .first()
            .removePrefix(COOKIE_PREFIX)
            .removeSurrounding(COOKIE_QUOTE)
        sessionStorage.saveSession("$COOKIE_PREFIX$sessionValue")
    }.onFailure { e ->
        if (e is CancellationException) throw e
    }

    companion object {
        private const val HEADER_SET_COOKIE = "Set-Cookie"
        private const val COOKIE_PREFIX = "spro_session="
        private const val COOKIE_SEPARATOR = ";"
        private const val COOKIE_QUOTE = "\""
        private const val ERROR_NO_COOKIE = "No session cookie in response"
    }
}