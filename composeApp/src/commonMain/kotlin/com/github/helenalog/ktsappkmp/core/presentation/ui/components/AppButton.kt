package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions

enum class AppButtonStyle { Primary, Outline }

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    style: AppButtonStyle = AppButtonStyle.Primary,
) {
    val buttonModifier = modifier.fillMaxWidth().height(Dimensions.buttonHeight)
    val buttonShape = RoundedCornerShape(Dimensions.buttonCornerRadius)
    val isEnabled = enabled && !isLoading

    val containerColor = when (style) {
        AppButtonStyle.Primary -> MaterialTheme.colorScheme.primary
        AppButtonStyle.Outline -> MaterialTheme.colorScheme.surface
    }.copy(alpha = if (isEnabled) 1f else 0.38f)

    val contentColor = when (style) {
        AppButtonStyle.Primary -> MaterialTheme.colorScheme.onPrimary
        AppButtonStyle.Outline -> MaterialTheme.colorScheme.primary
    }.copy(alpha = if (isEnabled) 1f else 0.38f)

    val border = when (style) {
        AppButtonStyle.Primary -> null
        AppButtonStyle.Outline -> BorderStroke(Dimensions.socialButtonBorderWidth, MaterialTheme.colorScheme.primary)
    }

    Surface(
        onClick = onClick,
        enabled = isEnabled,
        modifier = buttonModifier,
        shape = buttonShape,
        color = containerColor,
        contentColor = contentColor,
        border = border,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimensions.buttonLoaderSize),
                    color = LocalContentColor.current,
                    strokeWidth = Dimensions.buttonLoaderStrokeWidth
                )
            } else {
                Text(text = text, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppButtonPreview() {
    AppTheme {
        AppButton(text = "Войти", onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun AppButtonOutlinePreview() {
    AppTheme {
        AppButton(text = "Назад", onClick = {}, style = AppButtonStyle.Outline)
    }
}