package com.github.helenalog.ktsappkmp.feature.chat.presentation

import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ScenarioBlockUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ScenarioUi

sealed interface BotActionState {
    data object Idle : BotActionState
    data object Loading : BotActionState
    data class ScenarioPickerOpen(
        val scenarios: List<ScenarioUi>,
        val filteredScenarios: List<ScenarioUi> = scenarios,
        val query: String = ""
    ) : BotActionState

    data class BlockPickerOpen(
        val scenario: ScenarioUi,
        val blocks: List<ScenarioBlockUi>,
        val filteredBlocks: List<ScenarioBlockUi> = blocks,
        val query: String = ""
    ) : BotActionState

    data class Error(val message: String) : BotActionState
}
