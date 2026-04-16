package com.github.helenalog.ktsappkmp.feature.conversation.presentation

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.core.presentation.common.PaginationState
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationFilter
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationTab
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationTabUi
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationUi
import com.github.helenalog.ktsappkmp.feature.filter.presentation.model.ChannelUi
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.conversation_empty_all
import ktsappkmp.composeapp.generated.resources.conversation_empty_unread
import org.jetbrains.compose.resources.StringResource

@Immutable
data class ConversationUiState(
    val conversations: List<ConversationUi> = emptyList(),
    val tabs: List<ConversationTabUi> = emptyList(),
    val unreadCount: Int = 0,
    val selectedTab: ConversationTab = ConversationTab.ALL,
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val pagination: PaginationState = PaginationState(),
    val filter: ConversationFilter = ConversationFilter(),
    val normalizedFilter: ConversationFilter = ConversationFilter(),
    val availableChannels: List<ChannelUi> = emptyList(),
    val isFilterSheetOpen: Boolean = false,
    val hasAppliedFilter: Boolean = false,
    val totalUnreadCount: Int = 0
) {
    val hasActiveFilter: Boolean get() = !normalizedFilter.isEmpty
    val emptyMessage: StringResource
        get() = when (selectedTab) {
        ConversationTab.ALL -> Res.string.conversation_empty_all
        ConversationTab.UNREAD -> Res.string.conversation_empty_unread
    }

    companion object {
        val Initial = ConversationUiState()
    }
}
