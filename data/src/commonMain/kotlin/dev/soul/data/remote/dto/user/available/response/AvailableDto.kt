package dev.soul.data.remote.dto.user.available.response

import kotlinx.serialization.Serializable

@Serializable
data class AvailableDto(
    val created: String? = null,
    val day_of_week: Int? = null,
    val day_of_week_display: String? = null,
    val end_time: String? = null,
    val id: Int? = null,
    val is_active: Boolean? = null,
    val is_booked: Boolean? = null,
    val modified: String? = null,
    val price: String? = null,
    val stadium: Int? = null,
    val start_time: String? = null
)