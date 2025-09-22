package dev.soul.search.ui.map

import androidx.compose.runtime.Immutable
import dev.soul.domain.model.user.search.maps.StadiumModel
import dev.soul.domain.model.user.search.response.StadiumItemModel
import org.jetbrains.compose.resources.DrawableResource

@Immutable
data class MapSearchState(
    val isLoading: Boolean = false,
    val stadiums: List<StadiumModel> = emptyList(),
)