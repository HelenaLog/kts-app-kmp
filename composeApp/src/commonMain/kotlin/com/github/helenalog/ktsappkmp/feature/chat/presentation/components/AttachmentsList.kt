package com.github.helenalog.ktsappkmp.feature.chat.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.SubcomposeAsyncImage
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatAttachmentUi

@Composable
fun MessageAttachmentItem(
    attachment: ChatAttachmentUi,
    modifier: Modifier = Modifier
) {
    when (attachment) {
        is ChatAttachmentUi.Image -> SubcomposeAsyncImage(
            model = attachment.url,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = modifier
                .widthIn(max = Dimensions.attachmentMaxSize)
                .heightIn(max = Dimensions.attachmentMaxSize)
                .wrapContentSize()
                .clip(RoundedCornerShape(Dimensions.filterButtonCornerRadius)),
            loading = {
                CustomFileIcon(text = "IMG")
            },
            error = {
                CustomFileIcon(text = "ERR")
            }
        )

        is ChatAttachmentUi.File -> Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomFileIcon(text = attachment.typeLabel)
            Spacer(modifier = Modifier.width(Dimensions.spacingSmall))
            Column {
                Text(
                    text = attachment.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )
                Text(
                    text = attachment.sizeLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}