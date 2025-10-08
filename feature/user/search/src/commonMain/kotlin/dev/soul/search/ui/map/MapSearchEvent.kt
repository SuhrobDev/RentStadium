package dev.soul.search.ui.map

import dev.soul.domain.model.user.search.maps.StadiumModel
import dev.soul.search.SearchEvent

sealed interface MapSearchEvent {
    data object GetStadiums : MapSearchEvent
    data object Back : MapSearchEvent

    data class StadiumSelected(val stadium: StadiumModel) : MapSearchEvent

    data class OnMapMoved(val lat: Double, val long: Double) : MapSearchEvent
}