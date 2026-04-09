package com.github.helenalog.ktsappkmp.feature.chat.domain.model

data class ScenarioBlock(
    val id: String,
    val scenarioId: String,
    val kind: String,
    val type: ScenarioBlockType,
    val displayName: String
)
