package dev.soul.shared.utils

import dev.soul.shared.navigation.Screen
import kotlinx.serialization.Serializable



sealed class UiEvent {
    @Serializable
    data class Navigate(val route: Screen): UiEvent()
    @Serializable
    object NavigateUp: UiEvent()
    data class ShowSnackbar(val message: UiText): UiEvent()
}
