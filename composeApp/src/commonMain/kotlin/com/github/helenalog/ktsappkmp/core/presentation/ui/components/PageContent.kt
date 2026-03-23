package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.AppTheme
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.domain.model.OnboardingPage
import com.github.helenalog.ktsappkmp.domain.model.pages
import org.jetbrains.compose.resources.painterResource

@Composable
fun PageContent(
    page: OnboardingPage,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(page.imageRes),
            contentDescription = page.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.onboardingImageHeight),
            contentScale = ContentScale.Crop
        )
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(Dimensions.onboardingTextSpacing))
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = Dimensions.onboardingTextHorizontalPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PageContentPreview() {
    AppTheme {
        PageContent(page = pages.first())
    }
}