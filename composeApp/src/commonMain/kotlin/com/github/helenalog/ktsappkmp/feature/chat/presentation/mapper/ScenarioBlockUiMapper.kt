package com.github.helenalog.ktsappkmp.feature.chat.presentation.mapper

import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ScenarioBlock
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ScenarioBlockType
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ScenarioBlockUi
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.ic_edit
import ktsappkmp.composeapp.generated.resources.ic_send

class BlockUiMapper {
    fun map(domain: ScenarioBlock): ScenarioBlockUi = ScenarioBlockUi(
        id = domain.id,
        name = domain.displayName,
        kind = domain.kind,
        type = domain.type,
        icon = when (domain.type) {
            ScenarioBlockType.ACTION -> Res.drawable.ic_send
            ScenarioBlockType.INPUT -> Res.drawable.ic_edit
            ScenarioBlockType.UNKNOWN -> Res.drawable.ic_send
        }
    )

    fun mapList(domains: List<ScenarioBlock>): List<ScenarioBlockUi> = domains.map { map(it) }
}
