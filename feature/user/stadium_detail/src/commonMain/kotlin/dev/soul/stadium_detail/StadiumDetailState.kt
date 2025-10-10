package dev.soul.stadium_detail

import dev.soul.domain.model.user.available.response.AvailableModel
import dev.soul.domain.model.user.stadium_detail.response.StadiumDetailModel
import dev.soul.domain.model.user.upcoming_days.response.UpcomingDaysModel

data class StadiumDetailState(
    val isLoading: Boolean = false,
    val dateLoading: Boolean = false,
    val availableLoading: Boolean = false,
    val bookLoading: Boolean = false,
    val showConfirm: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val stadiumDetail: StadiumDetailModel? = null,
    val upcomingDays: List<UpcomingDaysModel> = emptyList(),
    val available: List<AvailableModel> = emptyList(),
    val selectedDate: Int? = null,
    val selectedAvailable: List<AvailableModel> = emptyList()
)
