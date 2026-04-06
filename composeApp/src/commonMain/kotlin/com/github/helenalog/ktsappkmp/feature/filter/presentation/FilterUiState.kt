package com.github.helenalog.ktsappkmp.feature.filter.presentation

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.displayName
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationFilter
import com.github.helenalog.ktsappkmp.feature.filter.presentation.model.ChannelUi
import com.github.helenalog.ktsappkmp.feature.filter.presentation.model.UserListUi

sealed interface FilterSheetType {
    data object Kinds : FilterSheetType
    data object Channels : FilterSheetType
    data object Lists : FilterSheetType
}

@Immutable
data class FilterUiState(
    val allChannelKinds: List<ChannelKind> = emptyList(),
    val selectedChannelKinds: Set<ChannelKind> = emptySet(),
    val allChannels: List<ChannelUi> = emptyList(),
    val selectedChannelIds: Set<String> = emptySet(),
    val userLists: List<UserListUi> = emptyList(),
    val selectedListId: String? = null,
    val kindsQuery: String = "",
    val channelsQuery: String = "",
    val userListQuery: String = "",
    val activeSheet: FilterSheetType? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val kindsLabel: String
        get() = if (selectedChannelKinds.isEmpty()) "Выберите элементы"
        else "Выбрано: ${selectedChannelKinds.size}"

    val channelsLabel: String
        get() = if (selectedChannelIds.isEmpty()) "Выберите каналы"
        else "Выбрано: ${selectedChannelIds.size}"

    val selectedListLabel: String
        get() = userLists.find { it.id == selectedListId }?.name ?: "Все пользователи"
    val filteredKinds: List<ChannelKind>
        get() = if (kindsQuery.isBlank()) allChannelKinds
        else allChannelKinds.filter { it.displayName.contains(kindsQuery, ignoreCase = true) }

    val filteredChannels: List<ChannelUi>
        get() = allChannels.filter { channel ->
            val matchesKind = selectedChannelKinds.isEmpty() || channel.kind in selectedChannelKinds
            val matchesQuery =
                channelsQuery.isBlank() || channel.name.contains(channelsQuery, ignoreCase = true)
            matchesKind && matchesQuery
        }

    val filteredUserLists: List<UserListUi>
        get() = if (userListQuery.isBlank()) userLists
        else userLists.filter { it.name.contains(userListQuery, ignoreCase = true) }

    val showAllUsersOption: Boolean
        get() = userListQuery.isBlank() || "Все пользователи".contains(
            userListQuery,
            ignoreCase = true
        )

    val canApply: Boolean
        get() = selectedChannelKinds.isNotEmpty() || selectedChannelIds.isNotEmpty()

    fun toFilter() = ConversationFilter(
        selectedChannelKinds = selectedChannelKinds,
        selectedChannelIds = selectedChannelIds,
        selectedListId = selectedListId
    )
}

