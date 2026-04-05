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
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.ObserveWsMessagesUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.SendMessageUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.StartBotUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.StopBotUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.UploadAttachmentUseCase
import com.github.helenalog.ktsappkmp.feature.chat.presentation.ChatViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val chatModule = module {

    factory { ChatApi(get(NetworkQualifier.MAIN)) }
    factory { ChatWebSocketApi(get(NetworkQualifier.MAIN)) }
    factory { BotActionApi(get(NetworkQualifier.MAIN)) }

    factory<ChatRepository> { ChatRepositoryImpl(api = get(), dateTimeParser = get()) }
    factory<BotActionRepository> { BotActionRepositoryImpl(api = get()) }
    factory<ConversationDetailRepository> { ConversationDetailRepositoryImpl(api = get()) }
    factory<ChatWebSocketRepository> {
        ChatWebSocketRepositoryImpl(
            wsClient = get(NetworkQualifier.WS),
            restApi = get(),
            networkConfig = get(),
            mapper = get(),
            json = get()
        )
    }
    factory<DateTimeParser> { DateTimeParserImpl() }

    factory { GetConversationDetailUseCase(get()) }
    factory { GetMessagesUseCase(get()) }
    factory { SendMessageUseCase(get()) }
    factory { StartBotUseCase(get()) }
    factory { StopBotUseCase(get()) }
    factory { UploadAttachmentUseCase(get()) }
    factory { ObserveWsMessagesUseCase(get()) }

    factory { UserAvatarUiMapper() }
    factory { ChatUiMapper(avatarMapper = get(), dateTimeParser = get()) }
    factory { WsMessageMapper(get()) }

    viewModel {
        ChatViewModel(
            getDetailUseCase = get(),
            getMessagesUseCase = get(),
            sendMessageUseCase = get(),
            uploadAttachmentUseCase = get(),
            observeMessagesUseCase = get(),
            startBotUseCase = get(),
            stopBotUseCase = get(),
            mapper = get(),
            avatarUiMapper = get()
        )
    }
}
