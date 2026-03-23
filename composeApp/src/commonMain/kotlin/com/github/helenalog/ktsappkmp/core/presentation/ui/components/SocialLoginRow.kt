package com.github.helenalog.ktsappkmp.core.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.helenalog.ktsappkmp.core.presentation.ui.theme.Dimensions
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.compose_multiplatform
import ktsappkmp.composeapp.generated.resources.google_icon
import ktsappkmp.composeapp.generated.resources.login_social_elama
import ktsappkmp.composeapp.generated.resources.login_social_google
import ktsappkmp.composeapp.generated.resources.login_social_telegram
import ktsappkmp.composeapp.generated.resources.login_social_vk
import ktsappkmp.composeapp.generated.resources.telegram_icon
import ktsappkmp.composeapp.generated.resources.vk_icon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private data class SocialLoginButton(
    val painter: DrawableResource,
    val contentDescription: StringResource
)

private val socialButtons = listOf(
    SocialLoginButton(Res.drawable.google_icon, Res.string.login_social_google),
    SocialLoginButton(Res.drawable.vk_icon, Res.string.login_social_vk),
    SocialLoginButton(Res.drawable.telegram_icon, Res.string.login_social_telegram),
    SocialLoginButton(Res.drawable.compose_multiplatform, Res.string.login_social_elama),
)

@Composable
fun SocialLoginRow(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.loginSpacingMedium, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        socialButtons.forEach { button ->
            SocialButton(
                painter = painterResource(button.painter),
                contentDescription = stringResource(button.contentDescription),
                onClick = { }
            )
        }
    }
}