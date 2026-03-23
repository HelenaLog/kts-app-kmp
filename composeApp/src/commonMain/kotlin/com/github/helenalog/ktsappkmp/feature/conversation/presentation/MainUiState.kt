package com.github.helenalog.ktsappkmp.feature.conversation.presentation

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.Conversation

@Immutable
data class MainUiState(
    val conversations: List<ConversationUi> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val pagination: PaginationState = PaginationState(),
) {
    companion object {
        val Initial = MainUiState()
    }
}