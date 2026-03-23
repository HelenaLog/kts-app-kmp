package com.github.helenalog.ktsappkmp.feature.login.presentation

sealed class LoginUiEvent {
    object LoginSuccessEvent : LoginUiEvent()
}