package dev.soul.domain.model.user.schedule.response

import dev.soul.domain.model.user.search.response.StadiumItemModel

data class ScheduleItemModel(
    val created: String,
    val datetimeRange: DatetimeRangeModel,
    val id: Int,
    val modified: String,
    val notes: String,
    val stadium: StadiumItemModel?,
    val status: ScheduleStatus
)

enum class ScheduleStatus {
    PENDING, ACCEPTED, CANCELLED, REJECTED
}

fun String.toStatus(): ScheduleStatus {
    return when (this) {
        "PENDING" -> ScheduleStatus.PENDING
        "ACCEPTED" -> ScheduleStatus.ACCEPTED
        "CANCELLED" -> ScheduleStatus.CANCELLED
        "REJECTED" -> ScheduleStatus.REJECTED
        else -> throw IllegalArgumentException("Invalid status: $this")

    }
}