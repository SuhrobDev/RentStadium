package dev.soul.data.remote.datasource.user.stadium_detail

import dev.soul.data.remote.dto.user.stadium_detail.response.StadiumDetailResponse
import dev.soul.network.MainResponse

interface StadiumDetailDatasource {
    suspend fun stadiumDetail(
        id: Int
    ): MainResponse<StadiumDetailResponse>
}