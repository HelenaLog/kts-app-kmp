package com.github.helenalog.ktsappkmp.feature.filter.presentation

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.core.presentation.common.BaseViewModel
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationFilter
import com.github.helenalog.ktsappkmp.feature.filter.domain.usecase.GetUserListUseCase
import com.github.helenalog.ktsappkmp.feature.filter.presentation.model.ChannelUi
import com.github.helenalog.ktsappkmp.feature.filter.presentation.model.UserListUi
import kotlinx.coroutines.launch

class FilterViewModel(
    private val getUserList: GetUserListUseCase,
) : BaseViewModel<FilterUiState, FilterUiEvent>(FilterUiState()) {

    private val allKinds = ChannelKind.entries.filter { it != ChannelKind.UNKNOWN }

    init {
        loadUserLists()
    }

    fun setFilter(
        availableChannels: List<ChannelUi>,
        currentFilter: ConversationFilter,
        isFirstOpen: Boolean
    ) {
        updateState {
            copy(
                allChannelKinds = allKinds,
                allChannels = availableChannels,
                selectedChannelKinds = if (isFirstOpen) allKinds.toSet()
                else currentFilter.selectedChannelKinds,
                selectedChannelIds = if (isFirstOpen) availableChannels.map { it.id }
                    .toSet() else currentFilter.selectedChannelIds,
                selectedListId = currentFilter.selectedListId,
                activeSection = FilterSection.MAIN,
                kindsQuery = "",
                channelsQuery = "",
                userListQuery = ""
            )
        }
    }

    fun toggleChannelKind(kind: ChannelKind) {
        updateState {
            val updatedKinds = if (kind in selectedChannelKinds) {
                selectedChannelKinds - kind
            } else {
                selectedChannelKinds + kind
            }

            val updatedChannelIds = selectedChannelIds.filter { id ->
                allChannels.any { it.id == id && it.kind in updatedKinds }
            }.toSet()

            copy(
                selectedChannelKinds = updatedKinds,
                selectedChannelIds = updatedChannelIds
            )
        }
    }

    fun selectAllChannelKinds() {
        updateState { copy(selectedChannelKinds = allKinds.toSet()) }
    }

    fun clearChannelKinds() {
        updateState { copy(selectedChannelKinds = emptySet()) }
    }

    fun toggleChannel(channelId: String) {
        updateState {
            copy(
                selectedChannelIds = if (channelId in selectedChannelIds) {
                    selectedChannelIds - channelId
                } else {
                    selectedChannelIds + channelId
                }
            )
        }
    }

    fun selectAllChannels() {
        updateState { copy(selectedChannelIds = filteredChannels.map { it.id }.toSet()) }
    }

    fun clearChannels() {
        updateState { copy(selectedChannelIds = emptySet()) }
    }

    fun selectList(listId: String?) {
        updateState { copy(selectedListId = listId) }
    }

    fun apply() {
        updateState { copy(activeSection = FilterSection.MAIN) }
        sendEvent(FilterUiEvent.Apply(state.value.toFilter()))
    }

    fun reset() {
        updateState {
            copy(
                selectedChannelKinds = emptySet(),
                selectedChannelIds = emptySet(),
                selectedListId = null,
                kindsQuery = "",
                channelsQuery = "",
                userListQuery = ""
            )
        }
    }

    fun openSection(section: FilterSection) {
        updateState { copy(activeSection = section) }
    }

    fun backToMain() {
        updateState { copy(activeSection = FilterSection.MAIN) }
    }

    fun retryLoadUserLists() {
        loadUserLists()
    }

    fun setQuery(section: FilterSection, value: String) {
        updateState {
            when (section) {
                FilterSection.KINDS -> copy(kindsQuery = value)
                FilterSection.CHANNELS -> copy(channelsQuery = value)
                FilterSection.USERS -> copy(userListQuery = value)
                FilterSection.MAIN -> this
            }
        }
    }

    private fun loadUserLists() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            getUserList()
                .onSuccess { lists ->
                    updateState {
                        copy(
                            userLists = lists.map { UserListUi(id = it.id, name = it.name) },
                            isLoading = false,
                        )
                    }
                }
                .onFailure { e ->
                    updateState { copy(error = e.message, isLoading = false) }
                }
        }
    }
}
