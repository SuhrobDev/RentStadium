package dev.soul.shared.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

// Helper function to format time from HH:mm:ss to HH:mm
fun formatTime(time: String): String {
    return if (time.length >= 5) {
        // Extract HH:mm from HH:mm:ss or HH:mm
        time.substring(0, 5)
    } else {
        time
    }
}

// Helper function to format price with thousand separators
fun formatPrice(price: Double): String {
    return price.toString().reversed().chunked(3).joinToString(" ").reversed()
}

@OptIn(ExperimentalTime::class)
fun formatDateAndTime(isoString: String): Pair<String, String> {
    // Parse ISO string to Instant
    val instant = Instant.parse(isoString)
    // Convert to local timezone (Uzbekistan UTC+5)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
    val month = localDateTime.month.name.lowercase().replaceFirstChar { it.titlecase() } // e.g. "October"
    val hour = localDateTime.hour.toString().padStart(2, '0')
    val minute = localDateTime.minute.toString().padStart(2, '0')

    val date = "$day $month"   // "13 October"
    val time = "$hour:$minute" // "06:00"

    return date to time
}

@OptIn(ExperimentalTime::class)
fun getDayOfWeek(isoString: String): String {
    val instant = Instant.parse(isoString)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val dayOfWeek = localDateTime.dayOfWeek // DayOfWeek enum

    // Capitalize nicely, e.g., "Monday"
    return dayOfWeek.name.lowercase().replaceFirstChar { it.titlecase() }
}