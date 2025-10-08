package dev.soul.user.home

import androidx.compose.runtime.Immutable
import dev.soul.domain.model.user.search.response.StadiumItemModel

@Immutable
data class HomeState(
    val personalizedLoading: Boolean = false,
    val popularLoading: Boolean = false,

    val personalizedList: List<StadiumItemModel> = emptyList(),
    val popularList: List<StadiumItemModel> = emptyList(),

    val success: Boolean = false,
)
