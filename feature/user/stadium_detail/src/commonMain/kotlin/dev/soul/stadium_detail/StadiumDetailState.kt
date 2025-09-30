package dev.soul.stadium_detail

import dev.soul.domain.model.user.stadium_detail.response.StadiumDetailModel

data class StadiumDetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val stadiumDetail: StadiumDetailModel? = null
)
