package com.github.helenalog.ktsappkmp.core.utils

import io.github.aakira.napier.Napier
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.number

object DateFormatter {

    @Suppress("TooGenericExceptionCaught")
    fun formatToShortTime(isoDateTime: String): String = try {
        isoDateTime.substring(11, 16)
    } catch (e: IndexOutOfBoundsException) {
        Napier.e("formatToShortTime failed", e)
        ""
    }

    @Suppress("TooGenericExceptionCaught")
    fun formatToIsoDate(isoDateTime: String): String = try {
        isoDateTime.substring(0, 10)
    } catch (e: IndexOutOfBoundsException) {
        Napier.e("formatToIsoDate failed", e)
        ""
    }

    @Suppress("TooGenericExceptionCaught")
    fun parseToLocalDate(isoDate: String): LocalDate? = try {
        LocalDate.parse(isoDate)
    } catch (e: IllegalArgumentException) {
        Napier.e("parseToLocalDate failed", e)
        null
    }

    fun formatDateLabel(isoDate: String?, today: LocalDate): String {
        val date = isoDate?.let { parseToLocalDate(it) } ?: return ""
        return when (date) {
            today -> "Сегодня"
            today.minus(DatePeriod(days = 1)) -> "Вчера"
            else -> "${date.day} ${monthName(date.month.number)}"
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

