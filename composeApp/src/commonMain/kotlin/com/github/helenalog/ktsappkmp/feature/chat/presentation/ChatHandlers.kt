package com.github.helenalog.ktsappkmp.feature.chat.presentation

import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ScenarioUi

data class NavigationHandlers(
    val onBack: () -> Unit,
    val onUserInfo: () -> Unit,
    val onRetry: () -> Unit
)

data class MessageHandlers(
    val onSend: () -> Unit,
    val onAttach: () -> Unit,
    val onLoadMore: () -> Unit
)

data class AttachmentHandlers(
    val onRemoveAttachment: (String) -> Unit,
    val onDismissAttachmentError: () -> Unit
)

data class BotActionHandlers(
    val onStopBot: () -> Unit,
    val onStartBot: () -> Unit,
    val onOpenBotActionSheet: () -> Unit,
    val onDismissBotAction: () -> Unit,
    val onSelectScenario: (ScenarioUi) -> Unit,
    val onRunScenario: (blockId: String) -> Unit,
    val onBackToScenarios: () -> Unit,
    val onSearchQueryChanged: (String) -> Unit
)

data class ChatHandlers(
    val navigation: NavigationHandlers,
    val message: MessageHandlers,
    val attachment: AttachmentHandlers,
    val botAction: BotActionHandlers
)
