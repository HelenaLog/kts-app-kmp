package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.ic_bot_preview
import org.jetbrains.compose.resources.painterResource

@Composable
fun MessageKindLabel(
    icon: Painter,
    label: String,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.messageKindSpacing)
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(Dimensions.messageKindIconSize)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = tint
        )
    }
}

@Preview
@Composable
private fun MessageKindLabelPreview() {
    MessageKindLabel(
        icon = painterResource(Res.drawable.ic_bot_preview),
        label = "(Бот)",
        tint = Color.Gray
    )
}
