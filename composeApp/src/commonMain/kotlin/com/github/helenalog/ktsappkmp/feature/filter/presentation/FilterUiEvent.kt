package com.github.helenalog.ktsappkmp.feature.filter.presentation

import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationFilter

sealed interface FilterUiEvent {
    data class Apply(val filter: ConversationFilter) : FilterUiEvent
}
