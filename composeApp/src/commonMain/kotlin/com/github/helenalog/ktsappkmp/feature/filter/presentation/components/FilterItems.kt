package com.github.helenalog.ktsappkmp.feature.filter.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun SelectableFilterRow(
    label: String,
    icon: DrawableResource,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterRowContainer(
        selected = selected,
        onClick = onClick,
        modifier = modifier
    ) {
        FilterCheckbox(checked = selected)
        Image(
            painter = painterResource(icon),
            contentDescription = label,
            modifier = Modifier.size(Dimensions.filterButtonIconSize)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun UserListRow(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterRowContainer(
        selected = selected,
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f)
        )
        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(Dimensions.filterButtonIconSize)
            )
        }
    }
}

@Composable
fun FilterCheckbox(
    checked: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(Dimensions.filterButtonIconSize)
            .clip(RoundedCornerShape(Dimensions.checkboxCornerRadius))
            .background(MaterialTheme.colorScheme.background)
            .border(
                width = Dimensions.socialButtonBorderWidth,
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(Dimensions.checkboxCornerRadius)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(Dimensions.checkIconSize)
            )
        }
    }
}
