package com.github.helenalog.ktsappkmp.core.presentation.banner

sealed interface BannersUiEvent {
    data class OpenUrl(val url: String) : BannersUiEvent
}
