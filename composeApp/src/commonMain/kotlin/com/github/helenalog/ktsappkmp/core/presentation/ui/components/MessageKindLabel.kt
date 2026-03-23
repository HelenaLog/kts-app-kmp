package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions

@Composable
fun MessageKindLabel(
    icon: ImageVector,
    label: String,
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.messageKindSpacing),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(Dimensions.messageKindIconSize),
            tint = tint,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = tint,
        )
    }
}

@Preview
@Composable
private fun MessageKindLabelPreview() {
    MessageKindLabel(
        icon = Icons.Default.SmartToy,
        label = "(Бот)",
        tint = Color.Gray,
    )
}