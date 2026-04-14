package com.github.helenalog.ktsappkmp.feature.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.chat_text_input_placeholder
import ktsappkmp.composeapp.generated.resources.chat_empty_message_error
import ktsappkmp.composeapp.generated.resources.ic_send_button
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ChatInputField(
    state: TextFieldState,
    onAttach: () -> Unit,
    onSend: () -> Unit,
    hasAttachments: Boolean = false,
    modifier: Modifier = Modifier
) {
    var showEmptyError by remember { mutableStateOf(false) }

    LaunchedEffect(state.text.length) {
        if (state.text.isNotBlank()) {
            showEmptyError = false
        }
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimensions.chatInputHorizontalPadding,
                    vertical = Dimensions.spacingSmall
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall)
        ) {
            BasicTextField(
                state = state,
                modifier = Modifier.weight(1f),
                lineLimits = TextFieldLineLimits.MultiLine(
                    minHeightInLines = 1,
                    maxHeightInLines = 3
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Send
                ),
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                decorator = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Dimensions.chatInputCornerRadius))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                            .border(
                                width = Dimensions.socialButtonBorderWidth,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(Dimensions.chatInputCornerRadius)
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(
                                    vertical = Dimensions.chatInputTextVerticalPadding,
                                    horizontal = Dimensions.spacingMedium
                                )
                        ) {
                            if (state.text.isEmpty()) {
                                Text(
                                    text = stringResource(Res.string.chat_text_input_placeholder),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                            innerTextField()
                        }
                        IconButton(
                            onClick = onAttach,
                            modifier = Modifier.padding(end = Dimensions.spacingExtraSmall)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AttachFile,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                }
            )
            SendButton(
                onClick = {
                    if (state.text.isBlank() && !hasAttachments) {
                        showEmptyError = true
                    } else {
                        showEmptyError = false
                        onSend()
                        state.clearText()
                    }
                }
            )
        }

        if (showEmptyError) {
            Text(
                text = stringResource(Res.string.chat_empty_message_error),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(
                    horizontal = Dimensions.chatInputHorizontalPadding
                )
            )
        }
    }
}

@Composable
fun SendButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(Dimensions.sendButtonSize)
            .clip(CircleShape)
            .background(
                color = if (enabled) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.primary.copy(alpha = 0.38f)
            )
            .clickable(enabled = enabled, onClick = onClick)
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_send_button),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier.size(Dimensions.sendButtonIconSize)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatInputFieldEmptyPreview() {
    AppTheme {
        ChatInputField(
            state = TextFieldState(),
            onAttach = {},
            onSend = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatInputFieldWithTextPreview() {
    AppTheme {
        ChatInputField(
            state = TextFieldState("Привет, как дела?"),
            onAttach = {},
            onSend = {}
        )
    }
}
