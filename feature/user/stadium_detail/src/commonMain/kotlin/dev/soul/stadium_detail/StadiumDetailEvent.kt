package dev.soul.stadium_detail

import dev.soul.domain.model.user.available.response.AvailableModel

sealed interface StadiumDetailEvent {
    data class Detail(val id: Int) : StadiumDetailEvent
    data class Share(val id: Int?) : StadiumDetailEvent
    data class DateSelect(val date: Int) : StadiumDetailEvent
    data class AvailableSelect(val available: AvailableModel) : StadiumDetailEvent
}