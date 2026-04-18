package com.github.helenalog.ktsappkmp.feature.conversation.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.helenalog.ktsappkmp.core.presentation.banner.BannersUiEvent
import com.github.helenalog.ktsappkmp.core.presentation.banner.BannersViewModel
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.BannerList
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.components.ConversationListItem
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.EmptyContent
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.ErrorContent
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.components.FilterButton
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.PaginationErrorFooter
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.SearchBar
import com.github.helenalog.ktsappkmp.core.presentation.workspace.WorkspaceTopBar
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationTab
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationTabUi
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationUi
import com.github.helenalog.ktsappkmp.feature.filter.presentation.FilterScreen
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    onConversationClick: (ConversationUi) -> Unit,
    onOpenUrl: (url: String) -> Unit,
    modifier: Modifier = Modifier,
    conversationViewModel: ConversationViewModel = koinViewModel(),
    bannersViewModel: BannersViewModel = koinViewModel()
) {
    val conversationState by conversationViewModel.state.collectAsStateWithLifecycle()
    val bannerState by bannersViewModel.state.collectAsStateWithLifecycle()
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(Unit) {
        bannersViewModel.event.collect { event ->
            when (event) {
                is BannersUiEvent.OpenUrl -> onOpenUrl(event.url)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            Column {
                WorkspaceTopBar(
                    modifier = Modifier.padding(
                        horizontal = Dimensions.topBarHorizontalPadding,
                        vertical = Dimensions.topBarVerticalPadding
                    )
                )
                MainTopBar(
                    searchQuery = conversationState.searchQuery,
                    onSearchQueryChange = { conversationViewModel.onSearchQueryChange(it) },
                    onClearSearch = { conversationViewModel.clearSearch() },
                    hasActiveFilter = conversationState.hasActiveFilter,
                    onFilterClick = { conversationViewModel.openFilterSheet() }
                )
                BannerList(
                    state = bannerState,
                    onAction = { bannersViewModel.onAction(it) },
                    onDismiss = { bannersViewModel.onDismiss(it) }
                )
            }
        },
        contentWindowInsets = WindowInsets()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                ConversationTabs(
                    tabs = conversationState.tabs,
                    onSelect = { conversationViewModel.selectTab(it) }
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    when {
                        conversationState.isLoading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        conversationState.error != null && conversationState.conversations.isEmpty() -> {
                            ErrorContent(
                                message = conversationState.error,
                                onRetry = { conversationViewModel.retry() },
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        conversationState.conversations.isEmpty() -> {
                            EmptyContent(
                                message = stringResource(conversationState.emptyMessage),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        else -> {
                            PullToRefreshBox(
                                isRefreshing = conversationState.isRefreshing,
                                onRefresh = { conversationViewModel.refresh() },
                                state = pullToRefreshState
                            ) {
                                ConversationList(
                                    conversations = conversationState.conversations,
                                    isPaginating = conversationState.pagination.isLoading,
                                    paginationError = conversationState.pagination.error,
                                    onReachEnd = { conversationViewModel.onReachEnd() },
                                    onRetryPagination = { conversationViewModel.onReachEnd() },
                                    onConversationClick = { conversation ->
                                        conversationViewModel.markAsRead(conversation.id)
                                        onConversationClick(conversation)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (conversationState.isFilterSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { conversationViewModel.closeFilterSheet() },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            dragHandle = null,
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            FilterScreen(
                availableChannels = conversationState.availableChannels,
                currentFilter = conversationState.filter,
                isFirstOpen = !conversationState.hasAppliedFilter,
                onApply = { filter -> conversationViewModel.applyFilter(filter) },
                onDismiss = { conversationViewModel.closeFilterSheet() },
                modifier = Modifier.fillMaxHeight(0.8f)
            )
        }
    }
}

@Composable
private fun ConversationTabs(
    tabs: List<ConversationTabUi>,
    onSelect: (ConversationTab) -> Unit,
    modifier: Modifier = Modifier
) {
    if (tabs.isEmpty()) return
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.topBarHorizontalPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEach { tab ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelect(tab.id) }
                        .padding(vertical = Dimensions.spacing12),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = tab.label,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (tab.isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimensions.topBarHorizontalPadding)
            ) {
                tabs.forEach { tab ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(Dimensions.tabIndicatorHeight)
                            .background(
                                if (tab.isSelected) MaterialTheme.colorScheme.primary
                                else Color.Transparent
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun ConversationList(
    conversations: List<ConversationUi>,
    isPaginating: Boolean,
    paginationError: String?,
    onReachEnd: () -> Unit,
    onRetryPagination: () -> Unit,
    onConversationClick: (ConversationUi) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = listState.layoutInfo.totalItemsCount
            total > 0 && lastVisible >= total - 3
        }
            .distinctUntilChanged()
            .filter { it }
            .collect { onReachEnd() }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall),
    ) {
        items(items = conversations, key = { it.id }) { conversation ->
            ConversationListItem(
                conversation = conversation,
                onConversationClick = onConversationClick
            )
        }

        if (isPaginating) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.spacingMedium),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(Dimensions.paginationLoaderSize))
                }
            }
        }

        if (paginationError != null) {
            item {
                PaginationErrorFooter(
                    message = paginationError,
                    onRetry = onRetryPagination
                )
            }
        }
    }
}

@Composable
private fun MainTopBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    hasActiveFilter: Boolean,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimensions.topBarHorizontalPadding,
                vertical = Dimensions.topBarVerticalPadding
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.topBarSpacing),
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            onClear = {
                onClearSearch()
                focusManager.clearFocus()
            },
            modifier = Modifier.weight(1f)
        )
        FilterButton(
            onClick = onFilterClick,
            isActive = hasActiveFilter
        )
    }
}

@Preview
@Composable
private fun MainScreenPrev() {
    ConversationScreen(
        conversationViewModel = koinViewModel(),
        onConversationClick = {},
        onOpenUrl = {}
    )
}
