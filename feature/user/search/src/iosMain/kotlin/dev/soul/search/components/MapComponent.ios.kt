package dev.soul.search.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import dev.soul.domain.model.user.search.maps.StadiumModel
import platform.UIKit.UIViewController

//@OptIn(ExperimentalForeignApi::class)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
actual fun MapComponent(
    stadiums: List<StadiumModel>,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onStadiumSelected: (StadiumModel) -> Unit
) {
    UIKitViewController(
        factory = mapViewController,
        modifier = Modifier.fillMaxSize(),
    )
}

lateinit var mapViewController: () -> UIViewController

