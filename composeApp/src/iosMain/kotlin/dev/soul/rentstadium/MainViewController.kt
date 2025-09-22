package dev.soul.rentstadium

import androidx.compose.ui.window.ComposeUIViewController
import dev.soul.di.initializeKoin
import dev.soul.search.components.mapViewController
import platform.UIKit.UIViewController

fun MainViewController(
    mapUIViewController: () -> UIViewController
) = ComposeUIViewController(
    configure = { initializeKoin() }
) {
    mapViewController = mapUIViewController
    App()
}