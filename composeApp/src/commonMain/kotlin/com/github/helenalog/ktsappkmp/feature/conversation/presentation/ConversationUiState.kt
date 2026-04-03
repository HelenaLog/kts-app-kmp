package com.github.helenalog.ktsappkmp.feature.conversation.presentation

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.core.presentation.common.PaginationState
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationUi

@Immutable
data class ConversationUiState(
    val conversations: List<ConversationUi> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val pagination: PaginationState = PaginationState(),
) {
    companion object Companion {
        val Initial = ConversationUiState()
    }
}
