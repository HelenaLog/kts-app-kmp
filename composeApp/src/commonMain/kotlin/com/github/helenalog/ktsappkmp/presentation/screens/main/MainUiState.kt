package com.github.helenalog.ktsappkmp.presentation.screens.main

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.data.remote.dto.ConversationDto

@Immutable
data class MainUiState(
    val conversations: List<ConversationDto> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val error: String? = null,
    val isPaginating: Boolean = false,
    val paginationError: String? = null,
    val hasReachedEnd: Boolean = false,
    val offset: Int = 0,
) {
    companion object {
        val Initial = MainUiState()
    }
}