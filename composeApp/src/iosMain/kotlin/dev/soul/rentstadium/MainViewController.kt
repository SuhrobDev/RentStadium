package dev.soul.rentstadium

import androidx.compose.ui.window.ComposeUIViewController
import dev.soul.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }