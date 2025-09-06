package dev.soul.data.remote.datasource.user.search

import dev.soul.data.remote.dto.user.search.response.StadiumItemResponse
import dev.soul.network.HttpParam
import dev.soul.network.HttpRoutes
import dev.soul.network.MainResponse
import dev.soul.network.PagingResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class SearchDatasourceImpl(
    private val httpClient: HttpClient
) : SearchDatasource {

    override suspend fun searchStadium(
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
    ): MainResponse<PagingResponse<StadiumItemResponse>> {
        return httpClient.get(HttpRoutes.STADIUM_LIST) {
            parameter(HttpParam.LAT, lat)
            parameter(HttpParam.LNG, lng)
            parameter(HttpParam.PAGE, page)
            parameter(HttpParam.PAGE_SIZE, size)

            name?.let {
                parameter(HttpParam.NAME, name)
            }
            type?.let {
                parameter(HttpParam.TYPE, type)
            }
            maxPrice?.let {
                parameter(HttpParam.MAX_PRICE, maxPrice)
            }
            minPrice?.let {
                parameter(HttpParam.MIN_PRICE, minPrice)
            }
            maxRate?.let {
                parameter(HttpParam.MAX_RATING, maxRate)
            }
            minRate?.let {
                parameter(HttpParam.MIN_RATING, minRate)
            }
        }.body()
    }

}