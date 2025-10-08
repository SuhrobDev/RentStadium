package dev.soul.user.home

sealed interface HomeEvent {
    data object Personalized : HomeEvent
    data object Popular : HomeEvent
    data class Detail(val id: Int) : HomeEvent

    data object MorePersonalized : HomeEvent
    data object MorePopular : HomeEvent
}