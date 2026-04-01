package com.github.helenalog.ktsappkmp.feature.chat.di

import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.UserAvatarUiMapper
import com.github.helenalog.ktsappkmp.core.data.remote.network.NetworkQualifier
import com.github.helenalog.ktsappkmp.feature.chat.data.mapper.WsChatMapper
import com.github.helenalog.ktsappkmp.feature.chat.presentation.mapper.ChatUiMapper
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.api.ChatApi
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.api.ChatWebSocketApi
import com.github.helenalog.ktsappkmp.feature.chat.data.repository.ChatRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.chat.data.repository.ChatWebSocketRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.chat.data.repository.ConversationDetailRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ChatRepository
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ChatWebSocketRepository
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ConversationDetailRepository
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.GetConversationDetailUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.GetMessagesUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.ObserveWsMessagesUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.SendMessageUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.UploadAttachmentUseCase
import com.github.helenalog.ktsappkmp.feature.chat.presentation.ChatViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.factory

val chatModule = module {

    factory { ChatApi(get(NetworkQualifier.MAIN)) }
    factory { ChatWebSocketApi(get(NetworkQualifier.MAIN)) }

    factory<ChatRepository> { ChatRepositoryImpl(api = get()) }
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

    factory { GetConversationDetailUseCase(get()) }
    factory { GetMessagesUseCase(get()) }
    factory { SendMessageUseCase(get()) }
    factory { UploadAttachmentUseCase(get()) }
    factory { ObserveWsMessagesUseCase(get()) }

    factory { UserAvatarUiMapper() }
    factory { ChatUiMapper(get()) }
    factory { WsChatMapper() }

    viewModel {
        ChatViewModel(
            getDetailUseCase = get(),
            getMessagesUseCase = get(),
            sendMessageUseCase = get(),
            uploadAttachmentUseCase = get(),
            observeMessagesUseCase = get(),
            mapper = get(),
            avatarUiMapper = get()
        )
    }
}
