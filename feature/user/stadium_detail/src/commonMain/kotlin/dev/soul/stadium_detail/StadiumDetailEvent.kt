package dev.soul.stadium_detail

sealed interface StadiumDetailEvent {
    data class Detail(val id: Int) : StadiumDetailEvent
}