package com.github.helenalog.ktsappkmp.screens.login.presentation.models

sealed class LoginUiEvent {
    object LoginSuccessEvent : LoginUiEvent()
}