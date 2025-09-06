package dev.soul.shared.utils

import dev.soul.shared.navigation.Screen


sealed class UiEvent {
    data class Navigate(val route: Screen): UiEvent()
    object NavigateUp: UiEvent()
    data class ShowSnackbar(val message: UiText): UiEvent()
}
