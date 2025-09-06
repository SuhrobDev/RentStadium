package dev.soul.shared.utils

import kotlinx.serialization.Serializable

@Serializable
sealed class TabScreens(val route: String) {

    @Serializable
    data object Home : TabScreens("Home")

    @Serializable
    data object Schedule : TabScreens("Schedule")

    @Serializable
    data object Search : TabScreens("Search")

    @Serializable
    data object Shop : TabScreens("Shop")

    @Serializable
    data object Profile : TabScreens("Profile")

}