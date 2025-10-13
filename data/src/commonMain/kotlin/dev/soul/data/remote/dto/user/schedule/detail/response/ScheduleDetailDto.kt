package dev.soul.data.remote.dto.user.schedule.detail.response

import dev.soul.data.remote.dto.user.schedule.response.DatetimeRange
import dev.soul.data.remote.dto.user.search.response.StadiumItemResponse
import dev.soul.data.remote.dto.user.stadium_detail.response.StadiumDetailResponse
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleDetailDto(
    val created: String? = null,
    val datetime_range: DatetimeRange? = null,
    val id: Int? = null,
    val modified: String? = null,
    val notes: String? = null,
    val stadium: StadiumDetailResponse? = null,
    val status: String? = null
)