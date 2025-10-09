package dev.soul.user.home

sealed interface HomeEvent {
    data object Personalized : HomeEvent
    data object Popular : HomeEvent
    data class Detail(val id: Int) : HomeEvent
    data class Like(val id: Int, val current: Boolean, val isPopular: Boolean) : HomeEvent
    data object MorePersonalized : HomeEvent
    data object MorePopular : HomeEvent
    data object Liked : HomeEvent
}