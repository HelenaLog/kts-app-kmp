package com.github.helenalog.ktsappkmp.feature.filter.presentation.model

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind

@Immutable
data class ChannelUi(
    val id: String,
    val name: String,
    val kind: ChannelKind
)
