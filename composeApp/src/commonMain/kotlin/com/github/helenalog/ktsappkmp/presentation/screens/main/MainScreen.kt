package com.github.helenalog.ktsappkmp.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.helenalog.ktsappkmp.domain.model.Conversation
import com.github.helenalog.ktsappkmp.presentation.ui.components.ConversationListItem
import com.github.helenalog.ktsappkmp.presentation.ui.components.EmptyState
import com.github.helenalog.ktsappkmp.presentation.ui.components.ErrorState
import com.github.helenalog.ktsappkmp.presentation.ui.components.FilterButton
import com.github.helenalog.ktsappkmp.presentation.ui.components.PaginationErrorFooter
import com.github.helenalog.ktsappkmp.presentation.ui.components.SearchBar
import com.github.helenalog.ktsappkmp.presentation.ui.theme.Dimensions
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel { MainViewModel() },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            MainTopBar(
                searchQuery = state.searchQuery,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                onClearSearch = viewModel::clearSearch
            )
        },
        contentWindowInsets = WindowInsets()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.error != null -> {
                    ErrorState(
                        message = state.error,
                        onRetry = viewModel::retry,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.conversations.isEmpty() -> {
                    EmptyState(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    ConversationList(
                        conversations = state.conversations,
                        isPaginating = state.isPaginating,
                        paginationError = state.paginationError,
                        onReachEnd = viewModel::onReachEnd,
                        onRetryPagination = viewModel::onReachEnd
                    )
                }
            }
        }
    }
}


@Composable
private fun ConversationList(
    conversations: List<Conversation>,
    isPaginating: Boolean,
    paginationError: String?,
    onReachEnd: () -> Unit,
    onRetryPagination: () -> Unit,
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
        verticalArrangement = Arrangement.spacedBy(Dimensions.itemSpacing),
    ) {
        items(items = conversations, key = { it.id }) {
            ConversationListItem(conversation = it)
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
    onFilterClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .systemBarsPadding()
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
            onClear = onClearSearch,
            modifier = Modifier.weight(1f)
        )
        FilterButton(onClick = onFilterClick)
    }
}

@Preview
@Composable
private fun MainScreenPrev() {
    MainScreen()
}