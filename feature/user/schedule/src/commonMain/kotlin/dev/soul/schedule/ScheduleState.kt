package dev.soul.schedule

import dev.soul.domain.model.user.schedule.response.ScheduleItemModel

data class ScheduleState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val schedules: List<ScheduleItemModel> = emptyList(),
)
