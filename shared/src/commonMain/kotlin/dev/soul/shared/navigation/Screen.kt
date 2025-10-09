package dev.soul.shared.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Validation : Screen()

    @Serializable
    data object Login : Screen()

    @Serializable
    data object RegPhone : Screen()

    @Serializable
    data class VerifyCode(val phone: String) : Screen()

    @Serializable
    data class Register(val phone: String) : Screen()

    @Serializable
    data object Base : Screen()

    @Serializable
    data object Notification : Screen()

    @Serializable
    data object MapSearch : Screen()

    @Serializable
    data object ByStadium : Screen()

    @Serializable
    data object BySaved : Screen()

    @Serializable
    data object Liked : Screen()

    @Serializable
    data object ScheduleHistory : Screen()

    @Serializable
    data class StadiumDetail(
        val id: Int,
    ) : Screen()

    @Serializable
    data class More(
        val isPersonalized: Boolean? = null,
        val isPopular: Boolean? = null
    ) : Screen()

}