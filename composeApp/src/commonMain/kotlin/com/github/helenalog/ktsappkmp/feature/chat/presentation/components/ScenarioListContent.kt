package com.github.helenalog.ktsappkmp.feature.chat.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ScenarioUi
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.chat_empty_scenarios
import ktsappkmp.composeapp.generated.resources.chat_scenario_search_placeholder
import ktsappkmp.composeapp.generated.resources.chat_scenario_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun ScenarioListContent(
    scenarios: List<ScenarioUi>,
    query: String,
    onQueryChange: (String) -> Unit,
    onSelectScenario: (ScenarioUi) -> Unit,
    modifier: Modifier = Modifier
) {
    BotActionSheetContent(
        subtitle = stringResource(Res.string.chat_scenario_title),
        query = query,
        onQueryChange = onQueryChange,
        searchPlaceholder = stringResource(Res.string.chat_scenario_search_placeholder),
        modifier = modifier
    ) {
        if (scenarios.isEmpty()) {
            item { EmptyListMessage(text = stringResource(Res.string.chat_empty_scenarios)) }
        } else {
            items(scenarios, key = { it.id }) { scenario ->
                ScenarioItem(
                    scenario = scenario,
                    onClick = { onSelectScenario(scenario) }
                )
            }
        }
    }
}

@Composable
private fun ScenarioItem(
    scenario: ScenarioUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(Dimensions.spacingMedium)
    ) {
        Text(
            text = scenario.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScenarioListContentPreview() {
    AppTheme {
        ScenarioListContent(
            scenarios = listOf(
                ScenarioUi(id = "1", name = "Сценарий онбординга"),
                ScenarioUi(id = "2", name = "Сценарий поддержки"),
                ScenarioUi(id = "3", name = "Сценарий продаж"),
            ),
            query = "",
            onQueryChange = {},
            onSelectScenario = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScenarioListContentWithQueryPreview() {
    AppTheme {
        ScenarioListContent(
            scenarios = listOf(
                ScenarioUi(id = "1", name = "Сценарий онбординга"),
            ),
            query = "онбор",
            onQueryChange = {},
            onSelectScenario = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScenarioListContentEmptyPreview() {
    AppTheme {
        ScenarioListContent(
            scenarios = emptyList(),
            query = "ничего",
            onQueryChange = {},
            onSelectScenario = {}
        )
    }
}
