package dev.soul.auth.otp

import androidx.compose.runtime.Immutable

@Immutable
data class VerifyState(
    val phone: String="",

    val code: String = "",
    val codes: List<Int?> = (1..5).map { null },
    val focusedIndex: Int? = null,
    val isValid: Boolean? = null,

    val totalTimeMillis: Long = 2 * 60 * 1000L,
    val timeLeft: Long = 0L,
    val isRunning: Boolean = false,

    val isLoading: Boolean = false,
    val isVerified: Boolean = false,
    val error: String? = null,
){
    val formattedTime: String
        get() {
            val minutes = (timeLeft / 1000) / 60
            val seconds = (timeLeft / 1000) % 60
            return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
        }
}