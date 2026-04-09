package com.github.helenalog.ktsappkmp.feature.chat.data.mapper

import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.ScenarioBlockDto
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ScenarioBlock
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ScenarioBlockType

fun ScenarioBlockDto.toDomain(specialVars: Map<String, String>) = ScenarioBlock(
    id = id,
    scenarioId = scenarioId,
    kind = kind,
    type = parseBlockType(type),
    displayName = resolveDisplayName(specialVars)
)

private fun parseBlockType(value: String): ScenarioBlockType = when (value) {
    "action" -> ScenarioBlockType.ACTION
    "input" -> ScenarioBlockType.INPUT
    else -> ScenarioBlockType.UNKNOWN
}

private fun ScenarioBlockDto.resolveDisplayName(specialVars: Map<String, String>): String {
    if (name.isNotBlank()) return name
    return when (kind) {
        "send_message" -> resolveSendMessageName()
        "chat_message" -> resolveChatMessageName(specialVars)
        else -> id
    }
}

private fun ScenarioBlockDto.resolveSendMessageName(): String =
    params?.markup?.takeIf { it.isNotBlank() } ?: id

private fun ScenarioBlockDto.resolveChatMessageName(specialVars: Map<String, String>): String {
    val condition = params?.messageConditions?.firstOrNull() ?: return id
    val keyTitle = condition.key?.let { specialVars[it] ?: it.replace("%", "") } ?: return id
    val value = condition.value?.takeIf { it.isNotBlank() } ?: return keyTitle
    return "$keyTitle = $value"
}
