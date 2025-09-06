package dev.soul.search

import androidx.compose.runtime.Immutable
import dev.soul.domain.model.user.search.response.StadiumItemModel
import org.jetbrains.compose.resources.DrawableResource

@Immutable
data class SearchState(
    val isLoading: Boolean = false,
    val isSearchFocused: Boolean = false,
    val isInitialLoading: Boolean = false,
    val isGiftLoading: Boolean = false,
    val endReached: Boolean = false,
    val hasError: Boolean = false,
    val canLoadMore: Boolean = false,
    val searchQuery: String = "",
    val error: String? = null,
    val currentPage: Int = 0,
    val totalPages: Int = 0,
    val options: List<Pair<DrawableResource, String>> = emptyList(),
    val types: List<Triple<DrawableResource, String, String>> = emptyList(),
    val stadiums: List<StadiumItemModel> = emptyList(),
)
