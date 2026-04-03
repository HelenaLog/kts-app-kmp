package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi

@Composable
fun UserAvatar(
    avatar: UserAvatarUi,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(Dimensions.avatarSize)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        if (avatar.showPhoto) {
            AsyncImage(
                model = avatar.photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = rememberVectorPainter(Icons.Default.Person),
                placeholder = rememberVectorPainter(Icons.Default.Person),
                modifier = Modifier.matchParentSize()
            )
        } else {
            Text(
                text = avatar.initials,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Preview
@Composable
private fun FriendAvatarPhotoPreview() {
    AppTheme {
        Row {
            UserAvatar(
                avatar = UserAvatarUi(
                    initials = "И",
                    photoUrl = "https://static.vecteezy.com/system/resources/previews/036/463/807/non_2x/ai-generated-young-caucasian-man-in-business-attire-portrait-png.png"
                )
            )
            UserAvatar(
                avatar = UserAvatarUi(
                    initials = "И",
                    photoUrl = null
                )
            )
        }
    }
}