package dev.soul.liked

sealed interface LikedEvent {
    data class Liked(val id: Int, val current: Boolean) : LikedEvent
    data class Detail(val id: Int) : LikedEvent
    object More : LikedEvent
    object Refresh : LikedEvent

}