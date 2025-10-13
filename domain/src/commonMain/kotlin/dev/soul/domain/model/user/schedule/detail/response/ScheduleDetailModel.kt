package dev.soul.domain.model.user.schedule.detail.response

import dev.soul.domain.model.user.schedule.response.DatetimeRangeModel
import dev.soul.domain.model.user.schedule.response.ScheduleStatus
import dev.soul.domain.model.user.search.response.StadiumItemModel
import dev.soul.domain.model.user.stadium_detail.response.StadiumDetailModel

data class ScheduleDetailModel(
    val created: String,
    val datetimeRange: DatetimeRangeModel?,
    val id: Int,
    val modified: String,
    val notes: String,
    val stadium: StadiumDetailModel?,
    val status: ScheduleStatus
)