package com.github.helenalog.ktsappkmp.feature.chat.di

import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.UserAvatarUiMapper
import com.github.helenalog.ktsappkmp.core.data.remote.network.NetworkQualifier
import com.github.helenalog.ktsappkmp.core.utils.DateTimeParser
import com.github.helenalog.ktsappkmp.core.utils.DateTimeParserImpl
import com.github.helenalog.ktsappkmp.feature.chat.data.mapper.WsMessageMapper
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.api.BotActionApi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.mapper.ChatUiMapper
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.api.ChatApi
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.api.ChatWebSocketApi
import com.github.helenalog.ktsappkmp.feature.chat.data.repository.BotActionRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.chat.data.repository.ChatRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.chat.data.repository.ChatWebSocketRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.chat.data.repository.ConversationDetailRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.BotActionRepository
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ChatRepository
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ChatWebSocketRepository
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ConversationDetailRepository
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.GetConversationDetailUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.GetMessagesUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.GetScenarioBlocksUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.GetScenariosUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.ObserveWsMessagesUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.RunScenarioUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.SendMessageUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.StartBotUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.StopBotUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.UploadAttachmentUseCase
import com.github.helenalog.ktsappkmp.feature.chat.presentation.ChatViewModel
import com.github.helenalog.ktsappkmp.feature.chat.presentation.mapper.BlockUiMapper
import com.github.helenalog.ktsappkmp.feature.chat.presentation.mapper.ScenarioUiMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatModule = module {

    factory { ChatApi(get(NetworkQualifier.MAIN)) }
    factory { ChatWebSocketApi(get(NetworkQualifier.MAIN)) }
    factory { BotActionApi(get(NetworkQualifier.MAIN)) }

    factoryOf(::ChatRepositoryImpl) bind ChatRepository::class
    factoryOf(::BotActionRepositoryImpl) bind BotActionRepository::class
    factoryOf(::ConversationDetailRepositoryImpl) bind ConversationDetailRepository::class
    factoryOf(::DateTimeParserImpl) bind DateTimeParser::class
    factory<ChatWebSocketRepository> {
        ChatWebSocketRepositoryImpl(
            wsClient = get(NetworkQualifier.WS),
            restApi = get(),
            networkConfig = get(),
            mapper = get(),
            json = get()
        )
    }

    factoryOf(::GetConversationDetailUseCase)
    factoryOf(::GetMessagesUseCase)
    factoryOf(::SendMessageUseCase)
    factoryOf(::StartBotUseCase)
    factoryOf(::StopBotUseCase)
    factoryOf(::UploadAttachmentUseCase)
    factoryOf(::ObserveWsMessagesUseCase)
    factoryOf(::GetScenariosUseCase)
    factoryOf(::RunScenarioUseCase)
    factoryOf(::GetScenarioBlocksUseCase)

    factoryOf(::UserAvatarUiMapper)
    factoryOf(::ChatUiMapper)
    factoryOf(::WsMessageMapper)
    factoryOf(::ScenarioUiMapper)
    factoryOf(::BlockUiMapper)

    viewModelOf(::ChatViewModel)
}
