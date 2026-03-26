package com.github.helenalog.ktsappkmp.feature.conversation.di

import com.github.helenalog.ktsappkmp.core.data.remote.network.NetworkQualifier
import com.github.helenalog.ktsappkmp.feature.conversation.data.api.ConversationsApi
import com.github.helenalog.ktsappkmp.feature.conversation.data.repository.ConversationRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationRepository
import com.github.helenalog.ktsappkmp.feature.conversation.domain.usecase.GetConversationsUseCase
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.ConversationViewModel
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.ConversationUiMapper
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val conversationModule = module {
    single { ConversationsApi(get(NetworkQualifier.MAIN)) }
    single<ConversationRepository> { ConversationRepositoryImpl(get(), get()) }
    single { GetConversationsUseCase(get()) }
    single { ConversationUiMapper() }

    viewModel { ConversationViewModel(get(), get()) }
}