package com.github.helenalog.ktsappkmp.feature.filter.presentation.mapper

import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.Channel
import com.github.helenalog.ktsappkmp.feature.filter.presentation.model.ChannelUi

fun Channel.toUi() = ChannelUi(id = id, name = name, kind = kind)
