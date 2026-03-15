package com.github.helenalog.ktsappkmp.presentation.screens.main

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.domain.model.Conversation

@Immutable
data class MainUiState(
    val conversations: List<Conversation> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val error: String? = null,
    val pagination: PaginationState = PaginationState(),
) {
    companion object {
        val Initial = MainUiState()
    }
}