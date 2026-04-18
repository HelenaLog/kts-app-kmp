package com.github.helenalog.ktsappkmp.feature.conversation.presentation

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.core.domain.activechat.usecase.ObserveActiveChatUseCase
import com.github.helenalog.ktsappkmp.core.domain.workspace.model.WorkspaceId
import com.github.helenalog.ktsappkmp.core.domain.workspace.usecase.ObserveActiveWorkspaceUseCase
import com.github.helenalog.ktsappkmp.core.presentation.common.BaseViewModel
import com.github.helenalog.ktsappkmp.core.presentation.common.PaginationState
import com.github.helenalog.ktsappkmp.feature.conversation.data.mapper.ConversationWsEventMapper
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationFilter
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationsPage
import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationWsEvent
import com.github.helenalog.ktsappkmp.feature.conversation.domain.usecase.GetConversationsUseCase
import com.github.helenalog.ktsappkmp.feature.conversation.domain.usecase.ObserveConversationUpdatesUseCase
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.ConversationTabUiMapper
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.ConversationUiMapper
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationTab
import com.github.helenalog.ktsappkmp.feature.filter.presentation.mapper.toUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ConversationViewModel(
    private val getConversations: GetConversationsUseCase,
    private val observeActiveWorkspace: ObserveActiveWorkspaceUseCase,
    private val observeConversationUpdates: ObserveConversationUpdatesUseCase,
    private val wsEventMapper: ConversationWsEventMapper,
    private val tabMapper: ConversationTabUiMapper,
    private val conversationMapper: ConversationUiMapper,
    private val observeActiveChatUseCase: ObserveActiveChatUseCase
) : BaseViewModel<ConversationUiState, Nothing>(ConversationUiState.Initial) {

    private val searchQueryFlow = MutableStateFlow("")
    private val filterFlow = MutableStateFlow(ConversationFilter())
    private var wsJob: Job? = null

    init {
        observeSearchAndFilter()
        observeWebSocket()
    }

    override fun onCleared() {
        super.onCleared()
        wsJob?.cancel()
    }

    fun onSearchQueryChange(query: String) {
        updateState { copy(searchQuery = query, isLoading = true, error = null) }
        searchQueryFlow.value = query
    }

    fun clearSearch() = onSearchQueryChange("")

    fun markAsRead(conversationId: Long) {
        updateState {
            val conversation = conversations.firstOrNull { it.id == conversationId }
                ?: return@updateState this
            if (conversation.isRead) return@updateState this

            val updatedConversations = conversations.map {
                if (it.id == conversationId) it.copy(isRead = true) else it
            }
            val newUnreadCount = (totalUnreadCount - 1).coerceAtLeast(0)
            copy(
                conversations = updatedConversations,
                totalUnreadCount = newUnreadCount,
                tabs = tabMapper.map(selectedTab, newUnreadCount)
            )
        }
    }

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
                    filter = filterFlow.value,
                    isRead = tabMapper.toIsRead(state.value.selectedTab)
                )
                    .onSuccess(::handlePage)
                    .onFailure(::handleRefreshError)
            } finally {
                updateState { copy(isRefreshing = false) }
            }
        }
    }

    fun selectTab(tab: ConversationTab) {
        updateState {
            copy(
                selectedTab = tab,
                tabs = tabMapper.map(tab, totalUnreadCount),
                isLoading = true,
                conversations = emptyList(),
                error = null,
                pagination = PaginationState()
            )
        }
        loadFirstPage(searchQueryFlow.value)
    }

    fun openFilterSheet() = updateState { copy(isFilterSheetOpen = true) }

    fun closeFilterSheet() = updateState { copy(isFilterSheetOpen = false) }

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

        if (shouldReload) filterFlow.value = normalized
    }

    private fun observeWebSocket() {
        wsJob = observeActiveWorkspace()
            .filterNotNull()
            .map { WorkspaceId(it.cabinet.id, it.project.id) }
            .distinctUntilChanged()
            .flatMapLatest { observeConversationUpdates() }
            .onEach { event -> handleWsEvent(event) }
            .launchIn(viewModelScope)
    }

    private fun handleWsEvent(event: ConversationWsEvent) {
        when (event) {
            is ConversationWsEvent.Connected -> Unit
            is ConversationWsEvent.NewMessage -> {
                val isCurrentlyOpen = observeActiveChatUseCase().value == event.conversationId
                val effectiveIsRead = event.isRead || isCurrentlyOpen
                val existsInList = state.value.conversations.any { it.id == event.conversationId }

                if (!existsInList) {
                    handleNewConversationMessage(effectiveIsRead)
                } else {
                    handleExistingConversationMessage(event, effectiveIsRead)
                }
            }
            is ConversationWsEvent.Reconnecting -> Unit
            is ConversationWsEvent.Error -> Unit
        }
    }

    private fun handleNewConversationMessage(effectiveIsRead: Boolean) {
        updateState {
            val newUnreadCount = if (!effectiveIsRead) totalUnreadCount + 1
            else totalUnreadCount
            copy(
                totalUnreadCount = newUnreadCount,
                tabs = tabMapper.map(selectedTab, newUnreadCount)
            )
        }
        loadFirstPage(searchQueryFlow.value)
    }

    private fun handleExistingConversationMessage(
        event: ConversationWsEvent.NewMessage,
        effectiveIsRead: Boolean,
    ) {
        val tabFilter = tabMapper.toFilter(state.value.selectedTab)
        updateState {
            val updatedConversations = wsEventMapper.applyNewMessage(
                conversations, event.copy(isRead = effectiveIsRead), tabFilter
            )
            val previousIsRead = conversations
                .firstOrNull { it.id == event.conversationId }?.isRead
            val newUnreadCount = when {
                selectedTab == ConversationTab.ALL ->
                    updatedConversations.count { !it.isRead }
                previousIsRead == false && effectiveIsRead ->
                    (totalUnreadCount - 1).coerceAtLeast(0)
                previousIsRead == true && !effectiveIsRead ->
                    totalUnreadCount + 1
                else -> totalUnreadCount
            }
            copy(
                conversations = updatedConversations,
                totalUnreadCount = newUnreadCount,
                tabs = tabMapper.map(selectedTab, newUnreadCount)
            )
        }
    }

    private fun observeSearchAndFilter() {
        combine(
            searchQueryFlow.debounce(SEARCH_DEBOUNCE_MS),
            filterFlow,
            observeActiveWorkspace()
                .filterNotNull()
                .map { WorkspaceId(it.cabinet.id, it.project.id) }
                .distinctUntilChanged()
                .onEach { updateState { copy(isLoading = true, error = null) } },
        ) { query, filter, _ -> query to filter }
            .flatMapLatest { (query, filter) ->
                flow {
                    emit(
                        getConversations(
                            query = query,
                            filter = filter,
                            isRead = tabMapper.toIsRead(state.value.selectedTab)
                        )
                    )
                }
            }
            .onEach { result ->
                result
                    .onSuccess(::handlePage)
                    .onFailure(::handleError)
            }
            .launchIn(viewModelScope)
    }

    private fun loadNextPage() {
        val s = state.value
        viewModelScope.launch {
            getConversations(
                query = s.searchQuery,
                offset = s.pagination.offset,
                filter = filterFlow.value,
                isRead = tabMapper.toIsRead(s.selectedTab)
            )
                .onSuccess(::handleNextPage)
                .onFailure(::handlePaginationError)
        }
    }

    private fun handleNextPage(page: ConversationsPage) {
        val newChannels = page.conversations
            .map { it.channel }
            .distinctBy { it.id }
            .map { it.toUi() }

        updateState {
            copy(
                conversations = conversations + conversationMapper.mapList(page.conversations),
                pagination = pagination.copy(
                    isLoading = false,
                    offset = pagination.offset + page.conversations.size,
                    hasMore = page.hasMore
                ),
                availableChannels = (availableChannels + newChannels).distinctBy { it.id }
            )
        }
    }

    private fun handlePaginationError(e: Throwable) {
        updateState {
            copy(pagination = pagination.copy(isLoading = false, error = e.message ?: PAGINATION_ERROR))
        }
    }

    private fun handleRefreshError(e: Throwable) {
        if (state.value.conversations.isEmpty()) handleError(e)
    }

    private fun handlePage(page: ConversationsPage) {
        val channels = page.conversations
            .map { it.channel }
            .distinctBy { it.id }
            .map { it.toUi() }

        val mapped = conversationMapper.mapList(page.conversations)

        updateState {
            val newUnreadCount = if (selectedTab == ConversationTab.ALL) {
                mapped.count { !it.isRead }
            } else {
                totalUnreadCount
            }
            copy(
                isLoading = false,
                conversations = mapped,
                error = null,
                totalUnreadCount = newUnreadCount,
                tabs = tabMapper.map(selectedTab, newUnreadCount),
                pagination = pagination.copy(offset = mapped.size, hasMore = page.hasMore),
                availableChannels = channels
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
                filter = filterFlow.value,
                isRead = tabMapper.toIsRead(state.value.selectedTab)
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
