package dev.soul.search.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import dev.soul.domain.model.user.search.maps.StadiumModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
expect fun MapComponent(
    stadiums: List<StadiumModel>,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onStadiumSelected: (StadiumModel) -> Unit
)
