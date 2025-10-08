package dev.soul.data.remote.datasource.user.home

import dev.soul.data.remote.dto.user.search.response.StadiumItemResponse
import dev.soul.network.MainResponse
import dev.soul.network.PagingResponse

interface HomeDatasource {
    suspend fun personalized(): MainResponse<PagingResponse<StadiumItemResponse>>

    suspend fun popular(
        page: Int,
        size: Int,
        lat: Double,
        lng: Double,
    ): MainResponse<PagingResponse<StadiumItemResponse>>
}