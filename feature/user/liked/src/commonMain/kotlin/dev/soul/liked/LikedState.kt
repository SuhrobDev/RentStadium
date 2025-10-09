package dev.soul.liked

import dev.soul.domain.model.user.search.response.StadiumItemModel

data class LikedState(
    val likedList: List<StadiumItemModel> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isRefreshing: Boolean = false,
    val currentPage: Int = 1,
    val hasNextPage: Boolean = false,
    val success: Boolean = false
)
