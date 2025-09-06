package dev.soul.data.remote.datasource.user.search

import dev.soul.data.remote.dto.user.search.response.StadiumItemResponse
import dev.soul.network.MainResponse
import dev.soul.network.PagingResponse

interface SearchDatasource {
    suspend fun searchStadium(
        page: Int,
        size: Int,
        name: String?,
        lat: Double,
        lng: Double,
        type: String?,
        maxPrice: Double?,
        minPrice: Double?,
        maxRate: Double?,
        minRate: Double?,
    ): MainResponse<PagingResponse<StadiumItemResponse>>
}