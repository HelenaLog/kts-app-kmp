package com.github.helenalog.ktsappkmp.feature.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ScenarioBlockType
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ScenarioBlockUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ScenarioUi
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.chat_block_search_placeholder
import ktsappkmp.composeapp.generated.resources.chat_button_back_to_scenarios
import ktsappkmp.composeapp.generated.resources.chat_button_select
import ktsappkmp.composeapp.generated.resources.chat_empty_blocks
import ktsappkmp.composeapp.generated.resources.ic_edit
import ktsappkmp.composeapp.generated.resources.ic_send
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BlockListContent(
    scenario: ScenarioUi,
    blocks: List<ScenarioBlockUi>,
    query: String,
    onQueryChange: (String) -> Unit,
    onBack: () -> Unit,
    onSelectBlock: (blockId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedBlock by remember { mutableStateOf<ScenarioBlockUi?>(null) }

    BotActionSheetContent(
        subtitle = scenario.name,
        query = query,
        onQueryChange = onQueryChange,
        searchPlaceholder = stringResource(Res.string.chat_block_search_placeholder),
        modifier = modifier,
        headerContent = {
            TextButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = stringResource(Res.string.chat_button_back_to_scenarios),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        },
        footerContent = {
            HorizontalDivider()
            BlockSelectionFooter(
                scenarioName = scenario.name,
                selectedBlock = selectedBlock,
                onConfirm = { selectedBlock?.let { onSelectBlock(it.id) } },
            )
        },
    ) {
        if (blocks.isEmpty()) {
            item { EmptyListMessage(text = stringResource(Res.string.chat_empty_blocks)) }
        } else {
            items(blocks, key = { it.id }) { block ->
                BlockItem(
                    block = block,
                    isSelected = block.id == selectedBlock?.id,
                    onClick = { selectedBlock = block },
                )
            }
        }
    }
}

@Composable
private fun BlockSelectionFooter(
    scenarioName: String,
    selectedBlock: ScenarioBlockUi?,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.spacingMedium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = scenarioName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (selectedBlock != null) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(Dimensions.channelIconSize),
                )
                Text(
                    text = selectedBlock.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Spacer(Modifier.width(Dimensions.spacingSmall))
        Button(
            onClick = onConfirm,
            enabled = selectedBlock != null,
            shape = RoundedCornerShape(Dimensions.buttonCornerRadius),
        ) {
            Text(
                text = stringResource(Res.string.chat_button_select),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
private fun BlockItem(
    block: ScenarioBlockUi,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.spacingMedium)
            .clip(RoundedCornerShape(Dimensions.buttonCornerRadius))
            .then(
                if (isSelected) Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                else Modifier
            )
            .clickable(onClick = onClick)
            .padding(horizontal = Dimensions.spacingSmall, vertical = Dimensions.spacingMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(block.icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(Dimensions.filterButtonIconSize)
        )
        Spacer(Modifier.width(Dimensions.spacingSmall))
        Text(
            text = block.name,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BlockListContentPreview() {
    AppTheme {
        BlockListContent(
            scenario = ScenarioUi(id = "1", name = "Сценарий онбординга"),
            blocks = listOf(
                ScenarioBlockUi(
                    id = "1",
                    name = "Приветствие",
                    type = ScenarioBlockType.ACTION,
                    kind = "",
                    icon = Res.drawable.ic_send
                ),
                ScenarioBlockUi(
                    id = "2",
                    name = "Ввод имени",
                    type = ScenarioBlockType.INPUT,
                    kind = "",
                    icon = Res.drawable.ic_edit
                ),
                ScenarioBlockUi(
                    id = "3",
                    name = "Отправка сообщения",
                    type = ScenarioBlockType.ACTION,
                    kind = "",
                    icon = Res.drawable.ic_edit,
                ),
            ),
            query = "",
            onQueryChange = {},
            onBack = {},
            onSelectBlock = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BlockListContentWithSelectionPreview() {
    AppTheme {
        BlockListContent(
            scenario = ScenarioUi(id = "1", name = "Сценарий онбординга"),
            blocks = listOf(
                ScenarioBlockUi(
                    id = "1",
                    name = "Приветствие",
                    type = ScenarioBlockType.ACTION,
                    kind = "",
                    icon = Res.drawable.ic_edit
                ),
                ScenarioBlockUi(
                    id = "2",
                    name = "Ввод имени",
                    type = ScenarioBlockType.INPUT,
                    kind = "",
                    icon = Res.drawable.ic_edit
                ),
            ),
            query = "",
            onQueryChange = {},
            onBack = {},
            onSelectBlock = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BlockListContentEmptyPreview() {
    AppTheme {
        BlockListContent(
            scenario = ScenarioUi(id = "1", name = "Сценарий онбординга"),
            blocks = emptyList(),
            query = "",
            onQueryChange = {},
            onBack = {},
            onSelectBlock = {}
        )
    }
}
