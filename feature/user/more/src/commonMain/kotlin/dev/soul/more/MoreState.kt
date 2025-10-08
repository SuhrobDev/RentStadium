package dev.soul.more

import dev.soul.domain.model.user.search.response.StadiumItemModel

data class MoreState(
    val personalizedList: List<StadiumItemModel> = emptyList(),
    val popularList: List<StadiumItemModel> = emptyList(),
    val personalizedLoading: Boolean = false,
    val popularLoading: Boolean = false,
    val isPersonalized: Boolean? = null,
    val isPopular: Boolean? = null,

    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isRefreshing: Boolean = false,
    val currentPage: Int = 0,
    val hasNextPage: Boolean = false,

    val success: Boolean = false
)