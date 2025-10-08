package dev.soul.data.remote.datasource.user.home

import dev.soul.data.remote.dto.user.search.response.StadiumItemResponse
import dev.soul.data.remote.dto.user.stadium_detail.response.StadiumDetailResponse
import dev.soul.network.HttpParam
import dev.soul.network.HttpRoutes
import dev.soul.network.MainResponse
import dev.soul.network.PagingResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class HomeDatasourceImpl(
    private val httpClient: HttpClient
) : HomeDatasource {

    override suspend fun personalized(
        page: Int,
        size: Int,
    ): MainResponse<PagingResponse<StadiumItemResponse>> {
        return httpClient.get(HttpRoutes.PERSONALIZED){
            parameter(HttpParam.PAGE, page)
            parameter(HttpParam.PAGE_SIZE, size)
        }.body()
    }

    override suspend fun popular(
        page: Int,
        size: Int,
        lat: Double,
        lng: Double
    ): MainResponse<PagingResponse<StadiumItemResponse>> {
        return httpClient.get(HttpRoutes.POPULAR) {
            parameter(HttpParam.LAT, lat)
            parameter(HttpParam.LNG, lng)
            parameter(HttpParam.PAGE, page)
            parameter(HttpParam.PAGE_SIZE, size)
        }.body()
    }
}