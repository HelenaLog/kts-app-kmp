package com.github.helenalog.ktsappkmp.feature.filter.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.AppButton
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.AppButtonStyle
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.SearchBar
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationFilter
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.displayName
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.icon
import com.github.helenalog.ktsappkmp.feature.filter.presentation.components.EmptyStateRow
import com.github.helenalog.ktsappkmp.feature.filter.presentation.components.FilterHeader
import com.github.helenalog.ktsappkmp.feature.filter.presentation.components.FilterSection
import com.github.helenalog.ktsappkmp.feature.filter.presentation.components.SelectableFilterRow
import com.github.helenalog.ktsappkmp.feature.filter.presentation.components.SelectionTriggerField
import com.github.helenalog.ktsappkmp.feature.filter.presentation.components.ToggleAllButton
import com.github.helenalog.ktsappkmp.feature.filter.presentation.components.UserListRow
import com.github.helenalog.ktsappkmp.feature.filter.presentation.model.ChannelUi
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.filter_all_users
import ktsappkmp.composeapp.generated.resources.filter_apply_button
import ktsappkmp.composeapp.generated.resources.filter_channel_kind_title
import ktsappkmp.composeapp.generated.resources.filter_channel_title
import ktsappkmp.composeapp.generated.resources.filter_empty_result
import ktsappkmp.composeapp.generated.resources.filter_load_error
import ktsappkmp.composeapp.generated.resources.filter_retry
import ktsappkmp.composeapp.generated.resources.filter_select_channels_hint
import ktsappkmp.composeapp.generated.resources.filter_select_items_hint
import ktsappkmp.composeapp.generated.resources.filter_user_list_title
import ktsappkmp.composeapp.generated.resources.selected_channels_count
import ktsappkmp.composeapp.generated.resources.selected_items_count
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    availableChannels: List<ChannelUi>,
    currentFilter: ConversationFilter,
    isFirstOpen: Boolean,
    onApply: (ConversationFilter) -> Unit,
    onDismiss: () -> Unit,
    viewModel: FilterViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(availableChannels, currentFilter, isFirstOpen) {
        viewModel.setFilter(
            availableChannels = availableChannels,
            currentFilter = currentFilter,
            isFirstOpen = isFirstOpen
        )
    }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is FilterUiEvent.Apply -> {
                    onApply(event.filter)
                    onDismiss()
                }
            }
        }
    }

    Column(modifier = modifier.fillMaxHeight()) {
        FilterHeader(onReset = { viewModel.reset() })
        HorizontalDivider()
        AnimatedContent(
            targetState = state.activeSection,
            transitionSpec = {
                val isForward = targetState != FilterSection.MAIN
                if (isForward) {
                    slideInHorizontally { it } + fadeIn() togetherWith
                            slideOutHorizontally { -it } + fadeOut()
                } else {
                    slideInHorizontally { -it } + fadeIn() togetherWith
                            slideOutHorizontally { it } + fadeOut()
                }
            },
            modifier = Modifier.weight(1f),
            label = "filter_section"
        ) { section ->
            when (section) {
                FilterSection.MAIN -> FilterMainContent(
                    state = state,
                    onOpenKinds = { viewModel.openSection(FilterSection.KINDS) },
                    onOpenChannels = { viewModel.openSection(FilterSection.CHANNELS) },
                    onOpenUserList = { viewModel.openSection(FilterSection.USERS) }
                )

                FilterSection.KINDS -> KindsSelectionContent(
                    state = state,
                    onToggle = { viewModel.toggleChannelKind(it) },
                    onQueryChange = { viewModel.setQuery(FilterSection.KINDS, it) },
                    onSelectAll = { viewModel.selectAllChannelKinds() },
                    onClearAll = { viewModel.clearChannelKinds() },
                    onBack = { viewModel.backToMain() }
                )

                FilterSection.CHANNELS -> ChannelsSelectionContent(
                    state = state,
                    onToggle = { viewModel.toggleChannel(it) },
                    onQueryChange = { viewModel.setQuery(FilterSection.CHANNELS, it) },
                    onSelectAll = { viewModel.selectAllChannels() },
                    onClearAll = { viewModel.clearChannels() },
                    onBack = { viewModel.backToMain() }
                )

                FilterSection.USERS -> UserSelectionContent(
                    state = state,
                    onSelect = { viewModel.selectList(it) },
                    onQueryChange = { viewModel.setQuery(FilterSection.USERS, it) },
                    onBack = { viewModel.backToMain() },
                    onRetry = { viewModel.retryLoadUserLists() }
                )
            }
        }
        HorizontalDivider()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.spacingMedium)
        ) {
            AppButton(
                text = stringResource(Res.string.filter_apply_button),
                onClick = { viewModel.apply() },
                style = AppButtonStyle.Primary,
                enabled = state.canApply,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun FilterMainContent(
    state: FilterUiState,
    onOpenKinds: () -> Unit,
    onOpenChannels: () -> Unit,
    onOpenUserList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(all = Dimensions.spacingMedium),
        verticalArrangement = Arrangement.spacedBy(Dimensions.spacingMedium)
    ) {
        FilterSection(title = stringResource(Res.string.filter_channel_kind_title)) {
            SelectionTriggerField(
                label = formatSelectedCount(state.selectedChannelKinds.size),
                onClick = onOpenKinds
            )
        }
        FilterSection(title = stringResource(Res.string.filter_channel_title)) {
            SelectionTriggerField(
                label = formatChannelsCount(state.selectedChannelIds.size),
                onClick = onOpenChannels
            )
        }
        FilterSection(title = stringResource(Res.string.filter_user_list_title)) {
            SelectionTriggerField(
                label = state.selectedListLabel.ifEmpty { stringResource(Res.string.filter_all_users) },
                onClick = onOpenUserList
            )
        }
    }
}

@Composable
private fun SelectionSectionHeader(
    title: String,
    onBack: () -> Unit,
    action: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimensions.spacingMedium,
                vertical = Dimensions.spacingSmall
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = onBack,
            contentPadding = PaddingValues(
                top = Dimensions.spacingSmall,
                end = Dimensions.spacingMedium,
                bottom = Dimensions.spacingSmall
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(Dimensions.filterButtonIconSize)
            )
            Spacer(modifier = Modifier.width(Dimensions.spacingSmall))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        action?.invoke()
    }
}

@Composable
private fun KindsSelectionContent(
    state: FilterUiState,
    onToggle: (ChannelKind) -> Unit,
    onQueryChange: (String) -> Unit,
    onSelectAll: () -> Unit,
    onClearAll: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        SelectionSectionHeader(
            title = stringResource(Res.string.filter_channel_kind_title),
            onBack = onBack,
            action = {
                ToggleAllButton(
                    selectedCount = state.selectedChannelKinds.size,
                    totalCount = state.allChannelKinds.size,
                    onSelectAll = onSelectAll,
                    onClearAll = onClearAll
                )
            },
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimensions.spacingMedium)
        ) {
            SearchBar(
                query = state.kindsQuery,
                onQueryChange = onQueryChange,
                onClear = { onQueryChange("") }
            )
            Spacer(Modifier.height(Dimensions.spacingSmall))

            if (state.filteredKinds.isEmpty()) {
                EmptyStateRow(text = stringResource(Res.string.filter_empty_result))
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall)) {
                    state.filteredKinds.forEach { kind ->
                        key(kind) {
                            SelectableFilterRow(
                                label = kind.displayName,
                                icon = kind.icon,
                                selected = kind in state.selectedChannelKinds,
                                onClick = { onToggle(kind) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChannelsSelectionContent(
    state: FilterUiState,
    onToggle: (String) -> Unit,
    onQueryChange: (String) -> Unit,
    onSelectAll: () -> Unit,
    onClearAll: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        SelectionSectionHeader(
            title = stringResource(Res.string.filter_channel_title),
            onBack = onBack,
            action = {
                ToggleAllButton(
                    selectedCount = state.selectedChannelIds.size,
                    totalCount = state.filteredChannels.size,
                    onSelectAll = onSelectAll,
                    onClearAll = onClearAll
                )
            },
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimensions.spacingMedium)
        ) {
            SearchBar(
                query = state.channelsQuery,
                onQueryChange = onQueryChange,
                onClear = { onQueryChange("") },
            )
            Spacer(Modifier.height(Dimensions.spacingSmall))

            if (state.filteredChannels.isEmpty()) {
                EmptyStateRow(text = stringResource(Res.string.filter_empty_result))
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall)) {
                    state.filteredChannels.forEach { channel ->
                        key(channel.id) {
                            SelectableFilterRow(
                                label = channel.name,
                                icon = channel.kind.icon,
                                selected = channel.id in state.selectedChannelIds,
                                onClick = { onToggle(channel.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UserSelectionContent(
    state: FilterUiState,
    onSelect: (String?) -> Unit,
    onQueryChange: (String) -> Unit,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        SelectionSectionHeader(
            title = stringResource(Res.string.filter_user_list_title),
            onBack = onBack
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimensions.spacingMedium)
        ) {
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimensions.spacingMedium),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(Dimensions.paginationLoaderSize)
                        )
                    }
                }

                state.error != null -> {
                    EmptyStateRow(text = stringResource(Res.string.filter_load_error))
                    Spacer(Modifier.height(Dimensions.spacingSmall))
                    AppButton(
                        text = stringResource(Res.string.filter_retry),
                        onClick = onRetry,
                        style = AppButtonStyle.Primary,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                else -> {
                    SearchBar(
                        query = state.userListQuery,
                        onQueryChange = onQueryChange,
                        onClear = { onQueryChange("") }
                    )
                    Spacer(Modifier.height(Dimensions.spacingSmall))

                    if (!state.showAllUsersOption && state.filteredUserLists.isEmpty()) {
                        EmptyStateRow(text = stringResource(Res.string.filter_empty_result))
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall)) {
                            if (state.showAllUsersOption) {
                                UserListRow(
                                    label = stringResource(Res.string.filter_all_users),
                                    selected = state.selectedListId == null,
                                    onClick = { onSelect(null) }
                                )
                            }
                            state.filteredUserLists.forEach { list ->
                                key(list.id) {
                                    UserListRow(
                                        label = list.name,
                                        selected = list.id == state.selectedListId,
                                        onClick = { onSelect(list.id) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun formatSelectedCount(count: Int): String {
    return if (count == 0) {
        stringResource(Res.string.filter_select_items_hint)
    } else {
        pluralStringResource(Res.plurals.selected_items_count, count, count)
    }
}

@Composable
private fun formatChannelsCount(count: Int): String {
    return if (count == 0) {
        stringResource(Res.string.filter_select_channels_hint)
    } else {
        pluralStringResource(Res.plurals.selected_channels_count, count, count)
    }
}
