package dev.soul.domain.model.user.schedule.response

data class ScheduleItemModel(
    val created: String,
    val datetimeRange: DatetimeRangeModel,
    val id: Int,
    val modified: String,
    val notes: String,
    val stadium: Int,
    val status: String
)
