package com.github.helenalog.ktsappkmp.feature.chat.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatAttachmentType
import com.github.helenalog.ktsappkmp.feature.chat.presentation.ChatAttachmentUi

@Composable
fun PendingAttachmentsRow(
    items: List<ChatAttachmentUi>,
    onRemove: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (items.isEmpty()) return

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            horizontal = Dimensions.screenPadding,
            vertical = Dimensions.spacingMedium
        ),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingMedium),
    ) {
        items(
            items = items,
            key = { it.id },
            contentType = { it.type }
        ) { attachment ->
            AttachmentItem(
                item = attachment,
                onRemove = { onRemove(attachment.id) }
            )
        }
    }
}

@Composable
fun AttachmentItem(
    item: ChatAttachmentUi,
    onRemove: (() -> Unit)? = null
) {
    when (item) {
        is ChatAttachmentUi.Image -> ImagePreview(data = item, onRemove = onRemove)
        is ChatAttachmentUi.File -> FilePreview(data = item, onRemove = onRemove)
    }
}

@Composable
private fun ImagePreview(
    data: ChatAttachmentUi.Image,
    onRemove: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .size(Dimensions.attachmentMaxSize)
            .clip(MaterialTheme.shapes.medium)
            .border(Dimensions.socialButtonBorderWidth, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium)
    ) {
        AsyncImage(
            model = data.url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        if (onRemove != null) {
            RemoveButton(
                onClick = onRemove,
                modifier = Modifier.align(Alignment.TopEnd).padding(Dimensions.spacingMedium)
            )
        }
    }
}

@Composable
private fun FilePreview(
    data: ChatAttachmentUi.File,
    onRemove: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .heightIn(min = Dimensions.attachmentMaxSize)
            .widthIn(max = Dimensions.attachmentMaxSize),
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(Dimensions.socialButtonBorderWidth, MaterialTheme.colorScheme.outlineVariant),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = Dimensions.spacingMedium,
                vertical = Dimensions.spacingMedium
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomFileIcon(text = data.typeLabel)
            Spacer(modifier = Modifier.width(Dimensions.spacingMedium))
            Column(
                modifier = Modifier.weight(1f, fill = false),
                verticalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall)
            ) {
                Text(
                    text = data.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = data.sizeLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(Dimensions.spacingMedium))
            if (onRemove != null) {
                RemoveButton(onClick = onRemove)
            }
        }
    }
}

@Composable
private fun RemoveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(Dimensions.stateIconSize)
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PendingAttachmentsRowPreview() {
    AppTheme {
        PendingAttachmentsRow(
            items = listOf(
                ChatAttachmentUi.Image(id = "1", url = ""),
                ChatAttachmentUi.File(
                    id = "2",
                    name = "document.pdf",
                    sizeLabel = "1.2 MB",
                    typeLabel = "PDF",
                    extension = "",
                    type = ChatAttachmentType.FILE
                ),
                ChatAttachmentUi.File(
                    id = "3",
                    name = "very_long_file_name_example.docx",
                    sizeLabel = "345 KB",
                    typeLabel = "DOC",
                    extension = "",
                    type = ChatAttachmentType.FILE
                    )
            ),
            onRemove = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ImagePreviewPreview() {
    AppTheme {
        AttachmentItem(
            item = ChatAttachmentUi.Image(id = "1", url = ""),
            onRemove = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilePreviewPreview() {
    AppTheme {
        AttachmentItem(
            item = ChatAttachmentUi.File(
                id = "2",
                name = "document.pdf",
                sizeLabel = "1.2 MB",
                typeLabel = "PDF",
                extension = "",
                type = ChatAttachmentType.FILE
            ),
            onRemove = {}
        )
    }
}