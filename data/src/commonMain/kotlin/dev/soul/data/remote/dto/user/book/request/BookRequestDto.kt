package dev.soul.data.remote.dto.user.book.request

import dev.soul.data.remote.dto.user.schedule.response.DatetimeRange
import kotlinx.serialization.Serializable

@Serializable
data class BookRequestDto(
    val datetime_range: DatetimeRange,
    val note: String,
    val stadium: Int,
    val status: String
)