package com.github.helenalog.ktsappkmp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.presentation.ui.theme.Dimensions

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.outline,
    onPageClick: (Int) -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.onboardingIndicatorSpacing)
    ) {
        repeat(pageSize) { page ->
            Box(
                modifier = Modifier
                    .size(Dimensions.onboardingIndicatorSize)
                    .clip(CircleShape)
                    .background(color = if (page == selectedPage) selectedColor else unselectedColor)
                    .clickable { onPageClick(page) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PageIndicatorPreview() {
    AppTheme {
        PageIndicator(pageSize = 3, selectedPage = 0)
    }
}