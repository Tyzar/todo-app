package com.tyzar.test.todoapp.core.formatter

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

const val saveDateFormat: String = "yyyyMMddHHmm"
const val displayOnlyDateFormat: String = "dd MMMM yyyy"
const val displayOnlyTimeFormat: String = "HH:mm"
const val displayDateTimeFormat1: String = "dd MMMM yyyy HH:mm"
const val pickerDateFormat: String = "yyyy-MM-dd"

fun formatDate(date: LocalDateTime, pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return date.format(formatter)
}

fun formatDate(date: LocalDate, pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return date.format(formatter)
}

fun formatTime(time: LocalTime, pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return time.format(formatter)
}

fun parseDateTime(dateStr: String, pattern: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return LocalDateTime.parse(dateStr, formatter)
}

fun parseLocalDate(dateStr: String, pattern: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return LocalDate.parse(dateStr, formatter)
}
