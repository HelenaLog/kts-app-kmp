package com.github.helenalog.ktsappkmp.feature.conversation.presentation

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.feature.conversation.data.mapper.ConversationUiMapper
import com.github.helenalog.ktsappkmp.core.presentation.common.BaseViewModel
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationsPage
import com.github.helenalog.ktsappkmp.feature.conversation.domain.usecase.GetConversationsUseCase
import io.github.aakira.napier.Napier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ConversationViewModel(
    private val getConversations: GetConversationsUseCase
) : BaseViewModel<ConversationUiState, Nothing>(ConversationUiState.Initial) {
    private val conversationMapper: ConversationUiMapper = ConversationUiMapper()
    private val searchQueryFlow = MutableStateFlow("")


    init {
        observeSearch()
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
            if (!isLoading && !pagination.isPaginating && !pagination.hasReachedEnd) {
                shouldLoad = true
                copy(pagination = pagination.copy(isPaginating = true, error = null))
            } else this
        }
        if (shouldLoad) loadNextPage()
    }

    fun refresh() {
        viewModelScope.launch {
            updateState { copy(isRefreshing = true) }
            try {
                getConversations(query = searchQueryFlow.value)
                    .onSuccess(::handlePage)
                    .onFailure(::handleRefreshError)
            } finally {
                updateState { copy(isRefreshing = false) }
            }
        }
    }

    private fun observeSearch() {
        searchQueryFlow
            .debounce(SEARCH_DEBOUNCE_MS)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                Napier.d("observeSearch query: $query")
                flow { emit(getConversations(query = query)) }
            }
            .onEach { result ->
                Napier.d("observeSearch result size: ${result.getOrNull()?.conversations?.size}")
                result.onSuccess(::handlePage).onFailure(::handleError)
            }
            .launchIn(viewModelScope)
    }

    private fun loadNextPage() {
        val s = state.value
        viewModelScope.launch {
            getConversations(query = s.searchQuery, offset = s.pagination.offset)
                .onSuccess(::handleNextPage)
                .onFailure(::handlePaginationError)
        }
    }

    private fun handleNextPage(page: ConversationsPage) {
        Napier.d("handleNextPage: ${page.conversations.size} items")
        updateState {
            copy(
                conversations = conversations + conversationMapper.mapList(page.conversations),
                pagination = pagination.copy(
                    isPaginating = false,
                    offset = pagination.offset + page.conversations.size,
                    hasReachedEnd = !page.hasMore
                )
            )
        }
    }

    private fun handlePaginationError(e: Throwable) {
        updateState {
            copy(
                pagination = pagination.copy(
                    isPaginating = false,
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
        updateState {
            copy(
                isLoading = false,
                conversations = conversationMapper.mapList(page.conversations),
                error = null,
                pagination = pagination.copy(
                    offset = page.conversations.size,
                    hasReachedEnd = !page.hasMore
                )
            )
        }
    }

    private fun handleError(e: Throwable) {
        updateState { copy(isLoading = false, error = e.message ?: UNKNOWN_ERROR) }
    }

    private fun loadFirstPage(query: String) {
        viewModelScope.launch {
            getConversations(query = query)
                .onSuccess(::handlePage)
                .onFailure(::handleError)
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 300L
        private const val PAGINATION_ERROR = "Pagination error"
        private const val UNKNOWN_ERROR = "Unknown error"
    }
}