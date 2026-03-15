package com.github.helenalog.ktsappkmp.presentation.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Light
private val Primary = Color(0xFF4457FF)
private val OnPrimary = Color(0xFFFFFFFF)
private val PrimaryContainer = Color(0xFFF2F4FE)
private val SecondaryContainer = Color(0xFFF3F5FF)
private val Secondary = Color(0xFF4D506B)
private val Background = Color(0xFFFFFFFF)
private val Surface = Color(0xFFFFFFFF)
private val OnBackground = Color(0xFF060926)
private val OnSurface = Color(0xFF060926)
private val Outline = Color(0xFFE9EBFB)
private val Fill = Color(0xFF7B7FA7)

// Dark
private val DarkPrimary = Color(0xFF6E84FF)
private val DarkBackground = Color(0xFF0F1628)
private val DarkSurface = Color(0xFF151C33)
private val DarkOnBackground = Color(0xFFFFFFFF)
private val DarkOutline = Color(0xFF2B3350)

// Channel colors
val TelegramColor = Color(0xFF2AABEE)
val WhatsAppColor = Color(0xFF25D366)
val JivoColor = Color(0xFFFF6B35)

val SocialButtonBorder = Color(0xFFDBDEF9)

val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnBackground,
    secondary = Secondary,
    onSecondary = Color.White,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnBackground,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    outline = Outline
)

val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = Color(0xFF0B1A4A),
    primaryContainer = Color(0xFF1A2A70),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF9DA1C6),
    onSecondary = Color.Black,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnBackground,
    outline = DarkOutline
)