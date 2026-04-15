package com.github.helenalog.ktsappkmp.core.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Instant

interface DateTimeParser {
    fun parse(raw: String?): Instant
    fun toLocalDate(instant: Instant): LocalDate
    fun formatTime(instant: Instant): String
    fun formatDateLabel(instant: Instant, today: LocalDate): String
    fun formatConversationTime(instant: Instant, today: LocalDate): String
}
