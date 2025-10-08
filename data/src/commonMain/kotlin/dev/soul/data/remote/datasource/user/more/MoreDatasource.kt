package dev.soul.data.remote.datasource.user.more

import dev.soul.data.remote.dto.user.search.response.StadiumItemResponse
import dev.soul.network.MainResponse
import dev.soul.network.PagingResponse

interface MoreDatasource {
    suspend fun personalized(
        page: Int,
        size: Int,
    ): MainResponse<PagingResponse<StadiumItemResponse>>

    suspend fun popular(
        page: Int,
        size: Int,
        lat: Double,
        lng: Double,
    ): MainResponse<PagingResponse<StadiumItemResponse>>
}