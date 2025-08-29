package dev.soul.shared.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Entry : Screen()

    @Serializable
    data object Login : Screen()

    @Serializable
    data object RegPhone : Screen()

    @Serializable
    data object VerifyCode : Screen()

    @Serializable
    data object Register : Screen()

    @Serializable
    data object Base : Screen()
}