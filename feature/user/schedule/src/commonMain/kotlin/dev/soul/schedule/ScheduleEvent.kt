package dev.soul.schedule

sealed interface ScheduleEvent {
    data class AddFriend(val id: Int) : ScheduleEvent

    data object Refresh : ScheduleEvent

    data class Detail(val id: Int) : ScheduleEvent

}