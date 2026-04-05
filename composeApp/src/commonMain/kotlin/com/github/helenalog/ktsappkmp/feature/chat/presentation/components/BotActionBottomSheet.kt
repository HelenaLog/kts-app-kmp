package com.github.helenalog.ktsappkmp.feature.chat.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
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
        AnimatedContent(
            targetState = state,
            transitionSpec = {
                fadeIn(tween(ANIMATION_DURATION_MS)) togetherWith fadeOut(tween(ANIMATION_DURATION_MS))
            },
            contentKey = { it::class },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            label = ANIMATION_LABEL
        ) { animatedState ->
            when (animatedState) {
                is BotActionState.ScenarioPickerOpen -> ScenarioListContent(
                    scenarios = animatedState.filteredScenarios,
                    query = animatedState.query,
                    onQueryChange = handlers.onSearchQueryChanged,
                    onSelectScenario = handlers.onSelectScenario
                )
                is BotActionState.BlockPickerOpen -> BlockListContent(
                    scenario = animatedState.scenario,
                    blocks = animatedState.filteredBlocks,
                    query = animatedState.query,
                    onQueryChange = handlers.onSearchQueryChanged,
                    onBack = handlers.onBackToScenarios,
                    onSelectBlock = handlers.onRunScenario
                )
                is BotActionState.Loading -> SheetLoadingContent()
                is BotActionState.Error -> SheetErrorContent(
                    message = animatedState.message,
                    onDismiss = handlers.onDismissBotAction
                )
                else -> Unit
            }
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

private const val ANIMATION_DURATION_MS = 300
private const val ANIMATION_LABEL = "BotActionContent"

@Preview(showBackground = true)
@Composable
private fun SheetLoadingPreview() {
    AppTheme {
        SheetLoadingContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun SheetErrorPreview() {
    AppTheme {
        SheetErrorContent(
            message = "Не удалось загрузить данные",
            onDismiss = {}
        )
    }
}
