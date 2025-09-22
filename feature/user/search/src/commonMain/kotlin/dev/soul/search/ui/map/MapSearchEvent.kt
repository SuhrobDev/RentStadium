package dev.soul.search.ui.map

import dev.soul.search.SearchEvent

sealed interface MapSearchEvent {
    data object GetStadiums : MapSearchEvent
    data object Back : MapSearchEvent
}