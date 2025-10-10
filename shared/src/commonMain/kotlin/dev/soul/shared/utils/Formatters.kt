package dev.soul.shared.utils

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
