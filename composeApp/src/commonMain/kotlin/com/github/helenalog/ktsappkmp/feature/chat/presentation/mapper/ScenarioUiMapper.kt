package com.github.helenalog.ktsappkmp.feature.chat.presentation.mapper

import com.github.helenalog.ktsappkmp.feature.chat.domain.model.Scenario
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ScenarioUi

class ScenarioUiMapper {
    fun map(domain: Scenario): ScenarioUi = ScenarioUi(id = domain.id, name = domain.name)
    fun mapList(domains: List<Scenario>): List<ScenarioUi> = domains.map { map(it) }
}
