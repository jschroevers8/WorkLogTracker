package worklogtracker.presentation.framework.datetime

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun formatDate(dateTimeString: String): String {
    return try {
        val parsed = LocalDateTime.parse(dateTimeString)
        parsed.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    } catch (e: Exception) {
        dateTimeString
    }
}