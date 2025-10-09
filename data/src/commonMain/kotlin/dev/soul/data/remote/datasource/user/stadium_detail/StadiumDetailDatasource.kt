package dev.soul.data.remote.datasource.user.stadium_detail

import dev.soul.data.remote.dto.user.available.response.AvailableDto
import dev.soul.data.remote.dto.user.stadium_detail.response.StadiumDetailResponse
import dev.soul.data.remote.dto.user.upcoming_days.response.UpcomingDaysDto
import dev.soul.network.MainResponse

interface StadiumDetailDatasource {
    suspend fun stadiumDetail(
        id: Int
    ): MainResponse<StadiumDetailResponse>

    suspend fun upcomingDays(): MainResponse<List<UpcomingDaysDto>>

    suspend fun available(id: Int, date: String): MainResponse<List<AvailableDto>>
}