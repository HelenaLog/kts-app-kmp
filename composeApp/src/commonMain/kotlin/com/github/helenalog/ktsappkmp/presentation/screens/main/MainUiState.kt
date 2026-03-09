package com.github.helenalog.ktsappkmp.presentation.screens.main

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.domain.model.ConversationDto

@Immutable
data class MainUiState(
    val conversations: List<ConversationDto> = emptyList()
) {
    companion object {
        val Initial = MainUiState()
    }
}