package com.github.helenalog.ktsappkmp.feature.chat.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions

@Composable
fun CustomFileIcon(
    modifier: Modifier = Modifier,
    text: String = "IMG",
) {
    Box(
        modifier = modifier.size(Dimensions.stateIconSize),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.InsertDriveFile,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.background,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomFileIconPreview() {
    AppTheme {
        CustomFileIcon()
    }
}
