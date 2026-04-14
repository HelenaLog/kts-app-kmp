package com.github.helenalog.ktsappkmp.core.utils

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.Instant

class DateTimeParserImpl : DateTimeParser {
    private val tz = TimeZone.currentSystemDefault()

    override fun parse(raw: String?): Instant =
        raw?.let { suspendRunCatching { Instant.parse(it) }.getOrNull() }
            ?: Instant.DISTANT_PAST

    override fun toLocalDate(instant: Instant): LocalDate =
        instant.toLocalDateTime(tz).date

    override fun formatTime(instant: Instant): String {
        val local = instant.toLocalDateTime(tz)
        val hours = local.hour.toString().padStart(2, '0')
        val minutes = local.minute.toString().padStart(2, '0')
        return "$hours:$minutes"
    }

    override fun formatDateLabel(instant: Instant, today: LocalDate): String {
        val date = toLocalDate(instant)
        return when (date) {
            today -> "Сегодня"
            today.minus(DatePeriod(days = 1)) -> "Вчера"
            else -> "${date.dayOfMonth} ${monthName(date.monthNumber)}"
        }
    }

    override fun formatConversationTime(instant: Instant, today: LocalDate): String {
        val date = toLocalDate(instant)
        return when {
            date == today -> formatTime(instant)
            date == today.minus(DatePeriod(days = 1)) -> "Вчера"
            else -> {
                val day = date.dayOfMonth.toString().padStart(2, '0')
                val month = date.monthNumber.toString().padStart(2, '0')
                val year = date.year.toString().takeLast(2)
                "$day.$month.$year"
            }
        }
    }

    private fun monthName(month: Int): String = when (month) {
        1 -> "января"; 2 -> "февраля"; 3 -> "марта"
        4 -> "апреля"; 5 -> "мая"; 6 -> "июня"
        7 -> "июля"; 8 -> "августа"; 9 -> "сентября"
        10 -> "октября"; 11 -> "ноября"; 12 -> "декабря"
        else -> ""
    }
}
