package com.github.helenalog.ktsappkmp.feature.chat.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.ErrorContent
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.feature.chat.presentation.BotActionHandlers
import com.github.helenalog.ktsappkmp.feature.chat.presentation.BotActionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BotActionBottomSheet(
    state: BotActionState,
    handlers: BotActionHandlers,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = handlers.onDismissBotAction,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) {
        when (state) {
            is BotActionState.BlockPickerOpen -> BlockListContent(
                scenario = state.scenario,
                blocks = state.filteredBlocks,
                query = state.query,
                onQueryChange = handlers.onSearchQueryChanged,
                onBack = handlers.onBackToScenarios,
                onSelectBlock = handlers.onRunScenario
            )

            is BotActionState.ScenarioPickerOpen -> ScenarioListContent(
                scenarios = state.filteredScenarios,
                query = state.query,
                onQueryChange = handlers.onSearchQueryChanged,
                onSelectScenario = handlers.onSelectScenario
            )

            is BotActionState.Loading -> SheetLoadingContent()
            is BotActionState.Error -> SheetErrorContent(
                message = state.message,
                onDismiss = handlers.onDismissBotAction
            )

            else -> Unit
        }
    }
}

@Composable
private fun SheetLoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimensions.onboardingImageHeight),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SheetErrorContent(
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimensions.onboardingImageHeight)
            .padding(Dimensions.spacingMedium),
        contentAlignment = Alignment.Center
    ) {
        ErrorContent(
            message = message,
            onRetry = onDismiss
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SheetLoadingPreview() {
    AppTheme {
        SheetLoadingContent()
    }
}
