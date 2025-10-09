package dev.soul.data.remote.dto.user.schedule.response

import kotlinx.serialization.Serializable

@Serializable
data class DatetimeRange(
    val lower: String?=null,
    val upper: String?=null
)