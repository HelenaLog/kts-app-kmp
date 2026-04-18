package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind

@Composable
fun AvatarWithChannel(
    avatar: UserAvatarUi,
    channelKind: ChannelKind,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        UserAvatar(avatar = avatar)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(Dimensions.channelIconBackgroundSize)
                .offset(Dimensions.channelIconOffset, Dimensions.channelIconOffset)
                .background(MaterialTheme.colorScheme.background, CircleShape)
                .align(Alignment.BottomEnd)
        ) {
            ChannelIcon(
                kind = channelKind,
                modifier = Modifier.size(Dimensions.channelIconSize)
            )
        }
    }
}

@Preview
@Composable
private fun AvatarWithChannelPreview() {
    AvatarWithChannel(
        avatar = UserAvatarUi(
            initials = "Иван",
            photoUrl = ""
        ),
        channelKind = ChannelKind.TG
    )
}
