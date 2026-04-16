package com.github.helenalog.ktsappkmp.core.presentation.workspace

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.SocialButtonBorder
import com.github.helenalog.ktsappkmp.feature.filter.presentation.components.UserListRow
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WorkspaceTopBar(
    modifier: Modifier = Modifier,
    viewModel: WorkspaceSelectorViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        when {
            state.error != null -> {
                Text(
                    text = state.error.orEmpty(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {
                WorkspaceSelectorRow(
                    state = state,
                    onCabinetSelect = { viewModel.onEvent(WorkspaceSelectorUiEvent.SelectCabinet(it)) },
                    onProjectSelect = { viewModel.onEvent(WorkspaceSelectorUiEvent.SelectProject(it)) }
                )
            }
        }
    }
}

@Composable
private fun WorkspaceSelectorRow(
    state: WorkspaceSelectorUiState,
    onCabinetSelect: (String) -> Unit,
    onProjectSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingMedium)
    ) {
        SelectionDropdown(
            activeLabel = state.activeCabinetName,
            isLoading = state.isLoading,
            items = state.cabinets,
            itemLabel = { it.name },
            itemIsSelected = { it.isSelected },
            onSelect = { onCabinetSelect(it.id) },
            modifier = Modifier.widthIn(min = Dimensions.dropdownMinWidth)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = SocialButtonBorder
        )
        SelectionDropdown(
            activeLabel = state.activeProjectName,
            isLoading = state.isLoading,
            items = state.projects,
            itemLabel = { it.name },
            itemIsSelected = { it.isSelected },
            onSelect = { onProjectSelect(it.id) },
            modifier = Modifier.widthIn(min = Dimensions.dropdownMinWidth)
        )
    }
}

@Composable
private fun <T> SelectionDropdown(
    activeLabel: String,
    isLoading: Boolean,
    items: List<T>,
    itemLabel: (T) -> String,
    itemIsSelected: (T) -> Boolean,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.wrapContentWidth()) {
        DropdownTrigger(
            label = activeLabel,
            expanded = expanded,
            isLoading = isLoading,
            onClick = { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            shape = RoundedCornerShape(Dimensions.dropdownMenuCornerRadius),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .width(Dimensions.dropdownMenuWidth)
        ) {
            items.forEach { item ->
                DropdownItem(
                    label = itemLabel(item),
                    isSelected = itemIsSelected(item),
                    onClick = {
                        expanded = false
                        onSelect(item)
                    }
                )
            }
        }
    }
}

@Composable
private fun DropdownTrigger(
    label: String,
    expanded: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(end = Dimensions.spacingSmall)
            .clickable(enabled = !isLoading, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedContent(
            targetState = isLoading to label,
            transitionSpec = {
                fadeIn(tween(CROSSFADE_MS)) togetherWith
                        fadeOut(tween(CROSSFADE_MS)) using
                        SizeTransform { _, _ -> tween(CROSSFADE_MS) }
            },
            label = "workspace_dropdown_trigger"
        ) { (loading, text) ->
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimensions.loaderSize)
                )
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        imageVector = if (expanded) Icons.Default.ArrowDropUp
                        else Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Composable
private fun DropdownItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        modifier = modifier,
        text = {
            UserListRow(
                label = label,
                selected = isSelected,
                onClick = onClick,
                modifier = Modifier.widthIn(min = Dimensions.dropdownMinWidth)
            )
        },
        onClick = onClick,
        contentPadding = PaddingValues(
            horizontal = Dimensions.spacingSmall,
            vertical = Dimensions.spacingExtraSmall
        )
    )
}

private const val CROSSFADE_MS = 220
