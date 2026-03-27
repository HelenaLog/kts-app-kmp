package com.github.helenalog.ktsappkmp.feature.chat.di

import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.UserAvatarUiMapper
import com.github.helenalog.ktsappkmp.core.data.remote.network.NetworkQualifier
import com.github.helenalog.ktsappkmp.feature.chat.presentation.mapper.ChatUiMapper
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.api.ChatApi
import com.github.helenalog.ktsappkmp.feature.chat.data.repository.ChatRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.chat.data.repository.ConversationDetailRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ChatRepository
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ConversationDetailRepository
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.GetConversationDetailUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.GetMessagesUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.SendMessageUseCase
import com.github.helenalog.ktsappkmp.feature.chat.domain.usecase.UploadAttachmentUseCase
import com.github.helenalog.ktsappkmp.feature.chat.presentation.ChatViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val chatModule = module {

    single { ChatApi(get(NetworkQualifier.MAIN)) }

    single<ChatRepository> { ChatRepositoryImpl(api = get()) }
    single<ConversationDetailRepository> { ConversationDetailRepositoryImpl(api = get()) }

    factory { GetConversationDetailUseCase(get()) }
    factory { GetMessagesUseCase(get()) }
    factory { SendMessageUseCase(get()) }
    factory { UploadAttachmentUseCase(get()) }

    factory { UserAvatarUiMapper() }
    factory { ChatUiMapper(get()) }

    viewModel {
        ChatViewModel(
            getDetailUseCase = get(),
            getMessagesUseCase = get(),
            sendMessageUseCase = get(),
            uploadAttachmentUseCase = get(),
            mapper = get(),
            avatarUiMapper = get()
        )
    }
}