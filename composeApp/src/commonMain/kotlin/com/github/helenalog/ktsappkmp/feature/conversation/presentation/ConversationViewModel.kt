package com.github.helenalog.ktsappkmp.feature.conversation.presentation

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.ConversationUiMapper
import com.github.helenalog.ktsappkmp.core.presentation.common.BaseViewModel
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationFilter
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationsPage
import com.github.helenalog.ktsappkmp.feature.conversation.domain.usecase.GetConversationsUseCase
import com.github.helenalog.ktsappkmp.feature.filter.presentation.mapper.toUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ConversationViewModel(
    private val getConversations: GetConversationsUseCase,
    private val conversationMapper: ConversationUiMapper
) : BaseViewModel<ConversationUiState, Nothing>(ConversationUiState.Initial) {
    private val searchQueryFlow = MutableStateFlow("")
    private val filterFlow = MutableStateFlow(ConversationFilter())


    init {
        observeSearchAndFilter()
    }

    fun onSearchQueryChange(query: String) {
        updateState { copy(searchQuery = query, isLoading = true, error = null) }
        searchQueryFlow.value = query
    }

    fun clearSearch() = onSearchQueryChange("")

    fun retry() {
        updateState { copy(isLoading = true, error = null) }
        loadFirstPage(searchQueryFlow.value)
    }

    fun onReachEnd() {
        var shouldLoad = false
        updateState {
            if (!isLoading && !pagination.isLoading && pagination.hasMore) {
                shouldLoad = true
                copy(pagination = pagination.copy(isLoading = true, error = null))
            } else this
        }
        if (shouldLoad) loadNextPage()
    }

    fun refresh() {
        viewModelScope.launch {
            updateState { copy(isRefreshing = true) }
            try {
                getConversations(
                    query = searchQueryFlow.value,
                    filter = filterFlow.value
                )
                    .onSuccess(::handlePage)
                    .onFailure(::handleRefreshError)
            } finally {
                updateState { copy(isRefreshing = false) }
            }
        }
    }

    fun openFilterSheet() {
        updateState { copy(isFilterSheetOpen = true) }
    }

    fun closeFilterSheet() {
        updateState { copy(isFilterSheetOpen = false) }
    }

    fun applyFilter(newFilter: ConversationFilter) {
        val allKinds = ChannelKind.entries.filter { it != ChannelKind.UNKNOWN }.toSet()
        val allChannelIds = state.value.availableChannels.map { it.id }.toSet()

        val normalized = newFilter.normalized(allKinds, allChannelIds)
        val shouldReload = normalized != filterFlow.value

        updateState {
            copy(
                filter = newFilter,
                normalizedFilter = normalized,
                isFilterSheetOpen = false,
                hasAppliedFilter = true,
                isLoading = if (shouldReload) true else isLoading,
            )
        }

        if (shouldReload) {
            filterFlow.value = normalized
        }
    }

    private fun observeSearchAndFilter() {
        searchQueryFlow
            .debounce(SEARCH_DEBOUNCE_MS)
            .combine(filterFlow) { query, filter -> query to filter }
            .distinctUntilChanged()
            .flatMapLatest { (query, filter) ->
                flow { emit(getConversations(query = query, filter = filter)) }
            }
            .onEach { result ->
                result.onSuccess(::handlePage).onFailure(::handleError)
            }
            .launchIn(viewModelScope)
    }

    private fun loadNextPage() {
        val s = state.value
        viewModelScope.launch {
            getConversations(
                query = s.searchQuery,
                offset = s.pagination.offset,
                filter = filterFlow.value
            )
                .onSuccess(::handleNextPage)
                .onFailure(::handlePaginationError)
        }
    }

    private fun handleNextPage(page: ConversationsPage) {
        updateState {
            copy(
                conversations = conversations + conversationMapper.mapList(page.conversations),
                pagination = pagination.copy(
                    isLoading = false,
                    offset = pagination.offset + page.conversations.size,
                    hasMore = page.hasMore
                )
            )
        }
    }

    private fun handlePaginationError(e: Throwable) {
        updateState {
            copy(
                pagination = pagination.copy(
                    isLoading = false,
                    error = e.message ?: PAGINATION_ERROR
                )
            )
        }
    }

    private fun handleRefreshError(e: Throwable) {
        if (state.value.conversations.isEmpty()) {
            handleError(e)
        }
    }

    private fun handlePage(page: ConversationsPage) {
        val channels = page.conversations
            .map { it.channel }
            .distinctBy { it.id }
            .map { it.toUi() }

        updateState {
            copy(
                isLoading = false,
                conversations = conversationMapper.mapList(page.conversations),
                error = null,
                pagination = pagination.copy(
                    offset = page.conversations.size,
                    hasMore = page.hasMore
                ),
                availableChannels = (availableChannels + channels).distinctBy { it.id }
            )
        }
    }

    private fun handleError(e: Throwable) {
        updateState { copy(isLoading = false, error = e.message ?: UNKNOWN_ERROR) }
    }

    private fun loadFirstPage(query: String) {
        viewModelScope.launch {
            getConversations(
                query = query,
                filter = filterFlow.value
            )
                .onSuccess(::handlePage)
                .onFailure(::handleError)
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 300L
        private const val PAGINATION_ERROR = "Ошибка пагинации"
        private const val UNKNOWN_ERROR = "Неизвестная ошибка"
    }
}
