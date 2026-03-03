package com.github.helenalog.ktsappkmp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.github.helenalog.ktsappkmp.presentation.ui.theme.Dimensions
import ktsappkmp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource

@Composable
fun FriendAvatar(
    photoUrl: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(Dimensions.avatarSize)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        if (!photoUrl.isNullOrEmpty()) {
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = rememberVectorPainter(Icons.Default.Person),
                placeholder = rememberVectorPainter(Icons.Default.Person),
                modifier = Modifier
                    .matchParentSize()
            )
        } else {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .matchParentSize()
            )
        }
    }
}

@Preview
@Composable
private fun FriendAvatarPhotoPreview() {
    Row {
        FriendAvatar(
            photoUrl = "https://static.vecteezy.com/system/resources/previews/036/463/807/non_2x/ai-generated-young-caucasian-man-in-business-attire-portrait-png.png"
        )
        FriendAvatar(photoUrl = null)
    }
}