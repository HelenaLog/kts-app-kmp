package com.github.helenalog.ktsappkmp.feature.chat.data.mapper

import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.MessageConditionDto
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.ScenarioBlockDto
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ScenarioBlock
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ScenarioBlockType
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

fun ScenarioBlockDto.toDomain(json: Json, specialVars: Map<String, String>) = ScenarioBlock(
    id = id,
    scenarioId = scenarioId,
    kind = kind,
    type = parseBlockType(type),
    displayName = resolveDisplayName(json, specialVars)
)

private fun parseBlockType(value: String): ScenarioBlockType = when (value) {
    "action" -> ScenarioBlockType.ACTION
    "input" -> ScenarioBlockType.INPUT
    else -> ScenarioBlockType.UNKNOWN
}

private fun ScenarioBlockDto.resolveDisplayName(
    json: Json,
    specialVars: Map<String, String>
): String {
    if (name.isNotBlank()) return name
    return when (kind) {
        "send_message" -> resolveSendMessageName()
        "chat_message" -> resolveChatMessageName(json, specialVars)
        "communicate_ai" -> "Общение со Smartbot AI"
        "notify_managers" -> resolveNotifyManagersName()
        "set_vars" -> resolveSetVarsName()
        "user_input" -> "Ввод данных пользователя"
        "add_row_google_sheets" -> "Добавить строку в Google Sheets"
        "condition" -> "Условие"
        else -> kind.replace("_", " ").replaceFirstChar { it.uppercase() }
    }
}

private fun ScenarioBlockDto.resolveNotifyManagersName(): String {
    val paramsObj = params as? JsonObject ?: return "Уведомить менеджеров"
    return paramsObj["markup"]?.jsonPrimitive?.content?.takeIf { it.isNotBlank() }
        ?.lines()?.firstOrNull { it.isNotBlank() }?.take(50) ?: "Уведомить менеджеров"
}

private fun ScenarioBlockDto.resolveSetVarsName(): String {
    val paramsArray = params as? JsonArray ?: return "Установить переменную"
    val first = paramsArray.firstOrNull() as? JsonObject ?: return "Установить переменную"
    val lhs = first["lhs"]?.jsonPrimitive?.content ?: return "Установить переменную"
    val rhs = first["rhs"]?.jsonPrimitive?.content ?: return lhs
    return "$lhs = $rhs"
}

private fun ScenarioBlockDto.resolveSendMessageName(): String {
    val paramsObj = params as? JsonObject ?: return id
    return paramsObj["markup"]?.jsonPrimitive?.content?.takeIf { it.isNotBlank() } ?: id
}

private fun ScenarioBlockDto.resolveChatMessageName(
    json: Json,
    specialVars: Map<String, String>
): String {
    val paramsObj = params as? JsonObject ?: return id
    val conditions = runCatching {
        paramsObj["message_conditions"]?.let {
            json.decodeFromJsonElement(ListSerializer(MessageConditionDto.serializer()), it)
        }
    }.getOrNull() ?: return id

    val condition = conditions.firstOrNull() ?: return id
    val keyTitle = condition.key?.let { specialVars[it] ?: it.replace("%", "") } ?: return id
    val value = condition.value?.takeIf { it.isNotBlank() } ?: return keyTitle
    return "$keyTitle = $value"
}
