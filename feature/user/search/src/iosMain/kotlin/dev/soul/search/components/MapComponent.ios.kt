package dev.soul.search.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import dev.soul.domain.model.user.search.maps.StadiumModel
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIViewController

//@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MapComponent(stadiums: List<StadiumModel>) {
    UIKitViewController(
        factory = mapViewController,
        modifier = Modifier.fillMaxSize(),
    )
}

lateinit var mapViewController: () -> UIViewController

