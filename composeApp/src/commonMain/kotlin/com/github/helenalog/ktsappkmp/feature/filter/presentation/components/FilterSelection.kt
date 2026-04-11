package com.github.helenalog.ktsappkmp.feature.filter.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.SocialButtonBorder
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.filter_deselect_all
import ktsappkmp.composeapp.generated.resources.filter_select_all
import org.jetbrains.compose.resources.stringResource

@Composable
fun SelectionTriggerField(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingIcon: ImageVector = Icons.Default.KeyboardArrowDown
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimensions.buttonCornerRadius))
            .border(
                Dimensions.socialButtonBorderWidth,
                color = SocialButtonBorder,
                shape = RoundedCornerShape(Dimensions.buttonCornerRadius)
            )
            .clickable { onClick() }
            .padding(Dimensions.spacing12),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            imageVector = trailingIcon,
            contentDescription = null,
            tint = SocialButtonBorder
        )
    }
}

@Composable
fun ToggleAllButton(
    selectedCount: Int,
    totalCount: Int,
    onSelectAll: () -> Unit,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (totalCount <= 0) return
    val isAllSelected = selectedCount == totalCount
    TextButton(
        onClick = { if (isAllSelected) onClearAll() else onSelectAll() },
        modifier = modifier
    ) {
        Text(
            text = if (isAllSelected) stringResource(Res.string.filter_deselect_all)
            else stringResource(Res.string.filter_select_all),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelMedium
        )
    }
}
