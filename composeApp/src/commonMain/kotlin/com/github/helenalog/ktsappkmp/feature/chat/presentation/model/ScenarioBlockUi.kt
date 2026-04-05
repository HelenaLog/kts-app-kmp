package com.github.helenalog.ktsappkmp.feature.chat.presentation.model

import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ScenarioBlockType
import org.jetbrains.compose.resources.DrawableResource

class ScenarioBlockUi(
    val id: String,
    val name: String,
    val kind: String,
    val type: ScenarioBlockType,
    val icon: DrawableResource
)
