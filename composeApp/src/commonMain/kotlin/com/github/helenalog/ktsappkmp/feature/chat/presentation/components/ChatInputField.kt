package com.github.helenalog.ktsappkmp.feature.chat.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.SocialButtonBorder
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.chat_text_input_placeholder
import ktsappkmp.composeapp.generated.resources.ic_send_button
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ChatInputField(
    state: TextFieldState,
    onAttach: () -> Unit,
    onEmoji: () -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimensions.chatInputHorizontalPadding,
                vertical = Dimensions.spacingSmall
            ),
        verticalAlignment = Alignment.Bottom,
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
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.background,
                    border = BorderStroke(Dimensions.socialButtonBorderWidth, SocialButtonBorder)
                ) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(
                                    vertical = Dimensions.chatInputTextVerticalPadding,
                                    horizontal = Dimensions.spacingSmall
                                )
                                .align(Alignment.Top),
                        ) {
                            if (state.text.isEmpty()) {
                                Text(
                                    text = stringResource(Res.string.chat_text_input_placeholder),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }
                            innerTextField()
                        }
                        Column(
                            modifier = Modifier.padding(bottom = Dimensions.spacingSmall),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            InputIconButton(
                                icon = Icons.Outlined.AttachFile,
                                contentDescription = null,
                                onClick = onAttach
                            )
                            InputIconButton(
                                icon = Icons.Outlined.SentimentSatisfied,
                                contentDescription = null,
                                onClick = onEmoji
                            )
                        }
                    }
                }
            }
        )
        SendButton(
            onClick = {
                onSend()
                state.clearText()
            }
        )
    }
}

@Composable
fun InputIconButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = Dimensions.inputIconSize,
    tint: Color = MaterialTheme.colorScheme.tertiary,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(Dimensions.stateIconSize)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
fun SendButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.size(Dimensions.sendButtonSize),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
            disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f),
        ),
        shape = RoundedCornerShape(Dimensions.buttonCornerRadius),
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_send_button),
            contentDescription = null,
            modifier = Modifier.size(Dimensions.sendButtonIconSize)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatInputFieldEmptyPreview() {
    AppTheme {
        ChatInputField(
            state = TextFieldState(),
            onAttach = {},
            onEmoji = {},
            onSend = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatInputFieldWithTextPreview() {
    AppTheme {
        ChatInputField(
            state = TextFieldState("Привет, как дела?"),
            onAttach = {},
            onEmoji = {},
            onSend = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SendButtonPreview() {
    AppTheme {
        Column {
            SendButton(onClick = {})
        }
    }
}