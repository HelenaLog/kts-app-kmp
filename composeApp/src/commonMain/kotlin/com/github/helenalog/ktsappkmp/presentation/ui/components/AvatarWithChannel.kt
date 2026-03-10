package com.github.helenalog.ktsappkmp.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.presentation.ui.theme.Dimensions

@Composable
fun AvatarWithChannel(
    photoUrl: String?,
    channelKind: ChannelKind,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        UserAvatar(photoUrl = photoUrl)
        ChannelIcon(
            kind = channelKind,
            modifier = Modifier
                .size(Dimensions.channelIconSize)
                .align(Alignment.BottomEnd),
        )
    }
}

@Preview
@Composable
private fun AvatarWithChannelPreview() {
    AvatarWithChannel(
        photoUrl = null,
        channelKind = ChannelKind.TG,
    )
}