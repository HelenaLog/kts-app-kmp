package com.github.helenalog.ktsappkmp.feature.login.data.repository

import com.github.helenalog.ktsappkmp.core.data.storage.SessionStorage
import com.github.helenalog.ktsappkmp.feature.login.domain.repository.LoginRepository
import com.github.helenalog.ktsappkmp.feature.login.data.remote.api.LoginApi
import kotlin.coroutines.cancellation.CancellationException

class LoginRepositoryImpl(
    private val api: LoginApi,
    private val sessionStorage: SessionStorage
) : LoginRepository {

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