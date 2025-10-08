package dev.soul.stadium_detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import platform.UIKit.UIViewController

@Composable
actual fun StadiumLocationMapComponent(lat: Double, lng: Double) {
    UIKitViewController(
        factory = stadiumMapViewController,
        modifier = Modifier.fillMaxSize(),
    )
}

lateinit var stadiumMapViewController: () -> UIViewController
