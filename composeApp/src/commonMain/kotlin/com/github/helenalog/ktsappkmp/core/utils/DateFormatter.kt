package com.github.helenalog.ktsappkmp.core.utils

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus

object DateFormatter {

    fun formatToShortTime(isoDateTime: String): String = try {
        isoDateTime.substring(11, 16)
    } catch (e: Exception) {
        ""
    }

    fun formatToIsoDate(isoDateTime: String): String = try {
        isoDateTime.substring(0, 10)
    } catch (e: Exception) {
        ""
    }

    fun parseToLocalDate(isoDate: String): LocalDate? = try {
        LocalDate.parse(isoDate)
    } catch (e: Exception) {
        null
    }

    fun formatDateLabel(isoDate: String?, today: LocalDate): String {
        val date = isoDate?.let { parseToLocalDate(it) } ?: return ""
        return when (date) {
            today -> "Сегодня"
            today.minus(DatePeriod(days = 1)) -> "Вчера"
            else -> "${date.dayOfMonth} ${monthName(date.monthNumber)}"
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

