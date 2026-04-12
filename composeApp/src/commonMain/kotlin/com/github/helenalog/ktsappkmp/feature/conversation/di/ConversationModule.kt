package com.github.helenalog.ktsappkmp.feature.conversation.di

import com.github.helenalog.ktsappkmp.core.data.remote.network.NetworkQualifier
import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.api.ConversationApi
import com.github.helenalog.ktsappkmp.feature.conversation.data.repository.ConversationRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationRepository
import com.github.helenalog.ktsappkmp.feature.conversation.domain.usecase.GetConversationsUseCase
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.ConversationViewModel
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.ConversationUiMapper
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.UserAvatarUiMapper
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val conversationModule = module {
    factory { ConversationApi(get(NetworkQualifier.MAIN)) }
    factory<ConversationRepository> { ConversationRepositoryImpl(get(), get()) }
    factory { GetConversationsUseCase(get()) }
    factory { UserAvatarUiMapper() }
    factory { ConversationUiMapper(get()) }

    viewModel {
        ConversationViewModel(
            getConversations = get(),
            conversationMapper = get(),
            observeActiveWorkspace = get()
        )
    }
}
