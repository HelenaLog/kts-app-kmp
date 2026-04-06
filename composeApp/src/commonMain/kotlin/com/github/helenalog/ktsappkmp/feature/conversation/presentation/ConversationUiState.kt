package com.github.helenalog.ktsappkmp.feature.conversation.presentation

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.core.presentation.common.PaginationState
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationFilter
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationUi
import com.github.helenalog.ktsappkmp.feature.filter.presentation.model.ChannelUi

@Immutable
data class ConversationUiState(
    val conversations: List<ConversationUi> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val pagination: PaginationState = PaginationState(),
    val filter: ConversationFilter = ConversationFilter(),
    val normalizedFilter: ConversationFilter = ConversationFilter(),
    val availableChannels: List<ChannelUi> = emptyList(),
    val isFilterSheetOpen: Boolean = false,
    val hasAppliedFilter: Boolean = false
) {
    val hasActiveFilter: Boolean get() = !normalizedFilter.isEmpty

    companion object {
        val Initial = ConversationUiState()
    }
}
