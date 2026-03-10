package com.github.helenalog.ktsappkmp.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.helenalog.ktsappkmp.presentation.ui.components.ConversationListItem
import com.github.helenalog.ktsappkmp.presentation.ui.components.FilterButton
import com.github.helenalog.ktsappkmp.presentation.ui.components.SearchBar
import com.github.helenalog.ktsappkmp.presentation.ui.theme.Dimensions

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
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimensions.itemSpacing, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = innerPadding
        ) {
            items(
                items = state.conversations,
                key = { it.id }
            ) {
                ConversationListItem(conversation = it)
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