package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.extensions.color
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.extensions.displayName
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.extensions.icon


@Composable
fun ChannelIcon(
    kind: ChannelKind,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = kind.icon,
        contentDescription = kind.displayName,
        tint = kind.color,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun ChannelIconPreview() {
    ChannelIcon(kind = ChannelKind.TG)
}