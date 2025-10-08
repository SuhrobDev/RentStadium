package dev.soul.more

interface MoreEvent {
    data class Type(val type: MoreType) : MoreEvent
    data class Detail(val id: Int) : MoreEvent
    object onLoadMore : MoreEvent

    object onRefresh : MoreEvent

    data class Like(val id: Int) : MoreEvent
}

enum class MoreType {
    POPULAR,
    PERSONALIZED
}