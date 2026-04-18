package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.displayName
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.icon
import org.jetbrains.compose.resources.painterResource

@Composable
fun ChannelIcon(
    kind: ChannelKind,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(kind.icon),
        contentDescription = kind.displayName,
        modifier = modifier
    )
}

@Preview
@Composable
private fun ChannelIconPreview() {
    ChannelIcon(kind = ChannelKind.TG)
}
