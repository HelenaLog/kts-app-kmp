package com.github.helenalog.ktsappkmp.feature.chat.data.mapper

import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.ScenarioDto
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.Scenario

fun ScenarioDto.toDomain() = Scenario(id = id, name = name)
