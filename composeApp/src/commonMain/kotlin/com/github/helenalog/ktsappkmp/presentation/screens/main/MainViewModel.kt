package com.github.helenalog.ktsappkmp.presentation.screens.main

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.data.remote.dto.ConversationsPage
import com.github.helenalog.ktsappkmp.data.repository.ConversationRepositoryImpl
import com.github.helenalog.ktsappkmp.domain.repository.ConversationRepository
import com.github.helenalog.ktsappkmp.presentation.common.BaseViewModel
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
import kotlin.coroutines.cancellation.CancellationException

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MainViewModel(
    private val repository: ConversationRepository = ConversationRepositoryImpl()
) : BaseViewModel<MainUiState, Nothing>(MainUiState.Initial) {

    private val searchQueryFlow = MutableStateFlow("")

    init {
        observeSearch()
    }

    fun observeSearch() {
        searchQueryFlow
            .debounce(SEARCH_DEBOUNCE_MS)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                Napier.d("observeSearch query: $query")
                flow { emit(repository.getConversations(query = query)) }
            }
            .onEach { result ->
                Napier.d("observeSearch result: $result")
                result.onSuccess(::handlePage).onFailure(::handleError) }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        updateState { copy(searchQuery = query, isLoading = true, error = null) }
        searchQueryFlow.value = query
    }

    fun clearSearch() = onSearchQueryChange("")

    private fun handlePage(page: ConversationsPage) {
        updateState {
            copy(
                isLoading = false,
                conversations = page.conversations,
                offset = page.conversations.size,
                hasReachedEnd = !page.hasMore,
                error = null
            )
        }
    }

    private fun handleError(e: Throwable) {
        if (e is CancellationException) throw e
        updateState { copy(isLoading = false, error = e.message ?: "Unknown error") }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 300L
    }
}