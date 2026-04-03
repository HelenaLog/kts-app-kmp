package com.github.helenalog.ktsappkmp.feature.chat.presentation

sealed interface ChatUiEvent {
    data object NavigateBack : ChatUiEvent
}
