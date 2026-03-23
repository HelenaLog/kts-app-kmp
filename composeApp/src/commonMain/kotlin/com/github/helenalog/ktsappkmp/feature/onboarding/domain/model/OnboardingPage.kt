package com.github.helenalog.ktsappkmp.feature.onboarding.domain.model

import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.onboarding_smartbot
import org.jetbrains.compose.resources.DrawableResource

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: DrawableResource,
)

val pages = listOf(
    OnboardingPage(
        title = "Познакомьтесь со Smartbot Pro",
        description = "Smartbot Pro — российская платформа для создания чат-ботов и ИИ-ассистентов. Автоматизируйте общение с клиентами, продажи и поддержку — без программистов и сложных настроек.",
        imageRes = Res.drawable.onboarding_smartbot,
    ),
    OnboardingPage(
        title = "Один инструмент для всего бизнеса",
        description = "Отвечайте клиентам 24/7 с ИИ-ассистентом, запускайте рассылки в Telegram и ВКонтакте, принимайте платежи прямо в боте и подключайте CRM — всё в едином окне.",
        imageRes =  Res.drawable.onboarding_smartbot,
    ),
    OnboardingPage(
        title = "Всё готово к запуску",
        description = "Визуальный конструктор сценариев, встроенный ИИ на базе OpenAI и YandexGPT, 20+ готовых шаблонов для старта за 10 минут. \nПервые 7 дней — бесплатно.",
        imageRes =  Res.drawable.onboarding_smartbot,
    ),
)