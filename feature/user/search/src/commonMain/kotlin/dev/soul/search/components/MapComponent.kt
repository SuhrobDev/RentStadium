package dev.soul.search.components

import androidx.compose.runtime.Composable
import dev.soul.domain.model.user.search.maps.StadiumModel

@Composable
expect fun MapComponent(stadiums: List<StadiumModel>)
