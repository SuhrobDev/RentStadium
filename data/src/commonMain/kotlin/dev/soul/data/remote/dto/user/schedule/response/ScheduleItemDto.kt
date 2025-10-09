package dev.soul.data.remote.dto.user.schedule.response

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleItemDto(
    val created: String? = null,
    val datetime_range: DatetimeRange? = null,
    val id: Int? = null,
    val modified: String? = null,
    val notes: String? = null,
    val stadium: Int? = null,
    val status: String? = null
)