package com.github.helenalog.ktsappkmp.presentation.screens.login

sealed class LoginUiEvent {
    object LoginSuccessEvent : LoginUiEvent()
}