package com.github.helenalog.ktsappkmp.feature.conversation.di

import com.github.helenalog.ktsappkmp.core.data.remote.network.NetworkQualifier
import com.github.helenalog.ktsappkmp.core.domain.activechat.usecase.ObserveActiveChatUseCase
import com.github.helenalog.ktsappkmp.core.utils.DateTimeParser
import com.github.helenalog.ktsappkmp.core.utils.DateTimeParserImpl
import com.github.helenalog.ktsappkmp.feature.conversation.data.mapper.ConversationWsEventMapper
import com.github.helenalog.ktsappkmp.feature.conversation.data.mapper.ConversationWsMessageMapper
import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.api.ConversationApi
import com.github.helenalog.ktsappkmp.feature.conversation.data.repository.ConversationRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.conversation.data.repository.ConversationWebSocketRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationRepository
import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationWebSocketRepository
import com.github.helenalog.ktsappkmp.feature.conversation.domain.usecase.GetConversationsUseCase
import com.github.helenalog.ktsappkmp.feature.conversation.domain.usecase.ObserveConversationUpdatesUseCase
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.ConversationViewModel
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.ConversationTabUiMapper
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.ConversationUiMapper
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.UserAvatarUiMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val conversationModule = module {
    factory { ConversationApi(get(NetworkQualifier.MAIN)) }
    factoryOf(::ConversationRepositoryImpl) bind ConversationRepository::class
    factoryOf(::GetConversationsUseCase)
    factoryOf(::UserAvatarUiMapper)
    factoryOf(::ConversationUiMapper)
    factoryOf(::ConversationTabUiMapper)
    factoryOf(::DateTimeParserImpl) bind DateTimeParser::class
    factoryOf(::ConversationRepositoryImpl) bind ConversationRepository::class
    factoryOf(::GetConversationsUseCase)
    factoryOf(::ObserveConversationUpdatesUseCase)
    factoryOf(::ObserveActiveChatUseCase)
    factoryOf(::UserAvatarUiMapper)
    factoryOf(::ConversationUiMapper)
    factoryOf(::ConversationWsEventMapper)
    factoryOf(::ConversationWsMessageMapper)
    factory<ConversationWebSocketRepository> {
        ConversationWebSocketRepositoryImpl(
            wsClient = get(NetworkQualifier.WS),
            wsApi = get(),
            networkConfig = get(),
            mapper = get<ConversationWsMessageMapper>(),
            json = get()
        )
    }

    viewModelOf(::ConversationViewModel)
}
