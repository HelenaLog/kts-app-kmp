package com.github.helenalog.ktsappkmp.feature.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.SearchBar
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.InfoBackground
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.InfoColor
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.chat_info_banner
import ktsappkmp.composeapp.generated.resources.chat_sheet_title
import ktsappkmp.composeapp.generated.resources.ic_attention
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BotActionSheetContent(
    subtitle: String,
    query: String,
    onQueryChange: (String) -> Unit,
    searchPlaceholder: String,
    modifier: Modifier = Modifier,
    headerContent: @Composable (() -> Unit)? = null,
    footerContent: @Composable (() -> Unit)? = null,
    listContent: LazyListScope.() -> Unit
) {
    Column(modifier = modifier.padding(bottom = Dimensions.bottomSpacing)) {
        SheetTitle(text = stringResource(Res.string.chat_sheet_title))
        InfoBanner(
            text = stringResource(Res.string.chat_info_banner),
            modifier = Modifier.padding(horizontal = Dimensions.spacingMedium)
        )
        headerContent?.invoke()
        SheetTitle(text = subtitle)
        HorizontalDivider()
        SearchBar(
            query = query,
            onQueryChange = onQueryChange,
            onClear = { onQueryChange("") },
            placeholder = searchPlaceholder,
            modifier = Modifier.padding(
                horizontal = Dimensions.spacingMedium,
                vertical = Dimensions.spacingSmall
            )
        )
        LazyColumn(
            modifier = Modifier.weight(1f, fill = false),
            verticalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall),
            contentPadding = PaddingValues(bottom = Dimensions.spacingSmall),
            content = listContent
        )
        footerContent?.invoke()
    }
}

@Composable
fun SheetTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimensions.spacingMedium,
                vertical = Dimensions.spacingSmall
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun InfoBanner(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = Dimensions.socialButtonBorderWidth,
                color = InfoColor,
                shape = RoundedCornerShape(Dimensions.buttonCornerRadius)
            )
            .background(
                color = InfoBackground,
                shape = RoundedCornerShape(Dimensions.buttonCornerRadius)
            )
            .padding(
                horizontal = Dimensions.spacingMedium,
                vertical = Dimensions.spacingMedium
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall)
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_attention),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(Dimensions.filterButtonIconSize)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
