package dev.soul.data.remote.dto.user.upcoming_days.response

import kotlinx.serialization.Serializable

@Serializable
data class UpcomingDaysDto(
    val date: String? = null,
    val weekday: String? = null
)
