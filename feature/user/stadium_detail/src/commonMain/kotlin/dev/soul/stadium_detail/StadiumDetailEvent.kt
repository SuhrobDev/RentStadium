package dev.soul.stadium_detail

import dev.soul.domain.model.user.available.response.AvailableModel

sealed interface StadiumDetailEvent {
    data class Detail(val id: Int) : StadiumDetailEvent
    data class Share(val id: Int?) : StadiumDetailEvent
    data class DateSelect(val date: Int) : StadiumDetailEvent
    data class AvailableSelect(val available: AvailableModel) : StadiumDetailEvent
    data class SelectedWeekTab(val dayOfWeek: String) : StadiumDetailEvent
    data object Book : StadiumDetailEvent
    data class ScheduleDetail(val id: Int) : StadiumDetailEvent

    data class DeleteSchedule(val id: Int) : StadiumDetailEvent
}