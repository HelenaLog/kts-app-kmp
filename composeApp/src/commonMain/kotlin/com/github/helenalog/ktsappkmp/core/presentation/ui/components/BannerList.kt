package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.github.helenalog.ktsappkmp.core.domain.banner.model.BannerAction
import com.github.helenalog.ktsappkmp.core.domain.banner.model.BannerType
import com.github.helenalog.ktsappkmp.core.presentation.banner.BannersUiState
import com.github.helenalog.ktsappkmp.core.presentation.banner.model.AppBannerUi
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.ErrorRed
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.ErrorRedBorder
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.ErrorRedContainer
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.SuccessContainer
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.SuccessGreen
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.SuccessOutline

@Composable
fun BannerList(
    state: BannersUiState,
    onAction: (AppBannerUi) -> Unit,
    onDismiss: (AppBannerUi) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        state.isLoading -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimensions.spacingMedium,
                        vertical = Dimensions.spacingSmall
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimensions.loaderSize)
                )
            }
        }
        state.error != null -> {
            Text(
                text = state.error,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimensions.spacingMedium,
                        vertical = Dimensions.spacingSmall
                    )
            )
        }
        state.banners.isNotEmpty() -> {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(
                        horizontal = Dimensions.spacingMedium,
                        vertical = Dimensions.spacingSmall
                    ),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall)
            ) {
                state.banners.forEach { banner ->
                    BannerItem(
                        banner = banner,
                        onActionClick = { onAction(banner) },
                        onDismissClick = { onDismiss(banner) },
                        modifier = Modifier.height(Dimensions.bannerShimmerHeight)
                    )
                }
            }
        }
    }
}

@Composable
private fun BannerItem(
    banner: AppBannerUi,
    onActionClick: () -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val palette = banner.type.palette()

    Surface(
        color = palette.background,
        shape = RoundedCornerShape(Dimensions.buttonCornerRadius),
        border = BorderStroke(Dimensions.socialButtonBorderWidth, palette.border),
        modifier = modifier.width(Dimensions.bannerWidth)
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = Dimensions.spacing12,
                vertical = Dimensions.spacing10
            ),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = palette.icon,
                contentDescription = null,
                tint = palette.accent,
                modifier = Modifier.size(Dimensions.botIconSize)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Dimensions.spacing10)
            ) {
                Text(
                    text = banner.message,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(Modifier.weight(1f))
                if (!banner.actionLabel.isNullOrBlank()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(onClick = onActionClick)
                    ) {
                        Text(
                            text = banner.actionLabel,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = palette.accent
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowForward,
                            contentDescription = null,
                            tint = palette.accent,
                            modifier = Modifier.size(Dimensions.channelIconSize)
                        )
                    }
                }
            }

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = palette.accent,
                modifier = Modifier
                    .size(Dimensions.bannerCloseIconSize)
                    .clickable { onDismissClick() }
            )
        }
    }
}

private data class BannerPalette(
    val background: Color,
    val border: Color,
    val accent: Color,
    val icon: ImageVector
)

@Composable
private fun BannerType.palette(): BannerPalette = when (this) {
    BannerType.ERROR -> BannerPalette(
        background = ErrorRedContainer,
        border = ErrorRedBorder,
        accent = ErrorRed,
        icon = Icons.Outlined.ErrorOutline
    )

    BannerType.INFO -> BannerPalette(
        background = SuccessContainer,
        border = SuccessOutline,
        accent = SuccessGreen,
        icon = Icons.Outlined.CheckCircle
    )
}

@Preview(showBackground = true)
@Composable
private fun BannerListPreview() {
    BannerList(
        state = BannersUiState(
            banners = listOf(
                AppBannerUi(
                    id = "1",
                    message = "Произошла ошибка подключения к серверу",
                    actionLabel = "Повторить",
                    action = BannerAction.None,
                    type = BannerType.ERROR
                ),
                AppBannerUi(
                    id = "2",
                    message = "Доступна новая версия приложения",
                    actionLabel = "Обновить",
                    action = BannerAction.OpenUrl("https://example.com"),
                    type = BannerType.INFO
                ),
                AppBannerUi(
                    id = "3",
                    message = "Плановые технические работы с 22:00 до 23:00",
                    actionLabel = null,
                    action = BannerAction.None,
                    type = BannerType.INFO
                )
            )
        ),
        onAction = {},
        onDismiss = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun BannerErrorPreview() {
    BannerList(
        state = BannersUiState(error = "Не удалось загрузить баннеры"),
        onAction = {},
        onDismiss = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun BannerLoadingPreview() {
    BannerList(
        state = BannersUiState(isLoading = true),
        onAction = {},
        onDismiss = {}
    )
}
