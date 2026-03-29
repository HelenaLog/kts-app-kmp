package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi

@Composable
fun AvatarWithChannel(
    avatar: UserAvatarUi,
    channelKind: ChannelKind,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        UserAvatar(avatar = avatar)
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
        avatar = UserAvatarUi(
            initials = "Иван",
            photoUrl = ""
        ),
        channelKind = ChannelKind.TG,
    )
}
