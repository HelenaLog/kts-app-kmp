package com.github.helenalog.ktsappkmp.feature.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.chat_bot_start
import ktsappkmp.composeapp.generated.resources.chat_bot_stop
import ktsappkmp.composeapp.generated.resources.chat_continue_bot
import ktsappkmp.composeapp.generated.resources.chat_restart_bot
import ktsappkmp.composeapp.generated.resources.chat_run_scenario
import ktsappkmp.composeapp.generated.resources.ic_run_scenario
import ktsappkmp.composeapp.generated.resources.ic_start_bot
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BotActionButton(
    isBotActive: Boolean,
    onStopBot: () -> Unit,
    onStartBot: () -> Unit,
    onOpenBotActionSheet: () -> Unit,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember(isBotActive) { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(
            onClick = { if (isBotActive) onStopBot() else menuExpanded = true },
            modifier = Modifier
                .clip(CircleShape)
                .size(Dimensions.socialButtonIconSize)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = if (isBotActive) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (isBotActive) stringResource(Res.string.chat_bot_stop)
                else stringResource(Res.string.chat_bot_start),
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier.size(Dimensions.filterButtonIconSize)
            )
        }

        if (!isBotActive) {
            BotStartMenu(
                expanded = menuExpanded,
                onDismiss = { menuExpanded = false },
                onStartBot = { menuExpanded = false; onStartBot() },
                onRunScenario = { menuExpanded = false; onOpenBotActionSheet() }
            )
        }
    }
}

@Composable
private fun BotStartMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onStartBot: () -> Unit,
    onRunScenario: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(Dimensions.buttonCornerRadius)
        ),
    ) {
        CustomDropdownItem(
            label = stringResource(Res.string.chat_continue_bot),
            leadingIcon = Res.drawable.ic_start_bot,
            onClick = onStartBot
        )
        CustomDropdownItem(
            label = stringResource(Res.string.chat_restart_bot),
            leadingIcon = Res.drawable.ic_start_bot,
            onClick = onStartBot
        )
        CustomDropdownItem(
            label = stringResource(Res.string.chat_run_scenario),
            leadingIcon = Res.drawable.ic_run_scenario,
            onClick = onRunScenario
        )
    }
}

@Composable
private fun CustomDropdownItem(
    label: String,
    leadingIcon: DrawableResource? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        modifier = modifier.widthIn(min = Dimensions.menuItemMinWidth),
        text = {
            Row(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(Dimensions.textFieldCornerRadius)
                    )
                    .padding(Dimensions.spacingMedium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall)
            ) {
                if (leadingIcon != null) {
                    Icon(
                        painter = painterResource(leadingIcon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = label,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        },
        onClick = onClick,
        contentPadding = PaddingValues(Dimensions.spacingSmall)
    )
}

@Preview(showBackground = true)
@Composable
private fun BotActionButtonActivePreview() {
    AppTheme {
        BotActionButton(
            isBotActive = true,
            onStopBot = {},
            onStartBot = {},
            onOpenBotActionSheet = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BotActionButtonInactivePreview() {
    AppTheme {
        BotActionButton(
            isBotActive = false,
            onStopBot = {},
            onStartBot = {},
            onOpenBotActionSheet = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BotActionButtonLoadingPreview() {
    AppTheme {
        BotActionButton(
            isBotActive = false,
            onStopBot = {},
            onStartBot = {},
            onOpenBotActionSheet = {}
        )
    }
}
