package dev.soul.data.remote.datasource.user.stadium_detail

import dev.soul.data.remote.dto.user.available.response.AvailableDto
import dev.soul.data.remote.dto.user.stadium_detail.response.StadiumDetailResponse
import dev.soul.data.remote.dto.user.upcoming_days.response.UpcomingDaysDto
import dev.soul.network.HttpParam
import dev.soul.network.HttpRoutes
import dev.soul.network.MainResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class StadiumDetailDatasourceImpl(
    private val httpClient: HttpClient
) : StadiumDetailDatasource {

    override suspend fun stadiumDetail(id: Int): MainResponse<StadiumDetailResponse> {
        return httpClient.get(HttpRoutes.STADIUM_DETAIL + "$id/").body()
    }

    override suspend fun upcomingDays(): MainResponse<List<UpcomingDaysDto>> {
        return httpClient.get(HttpRoutes.UPCOMING_DAYS).body()
    }

    override suspend fun available(id: Int, date: String): MainResponse<List<AvailableDto>> {
        return httpClient.get(HttpRoutes.AVAILABLE + "${id}/") {
            url {
                parameters.append(HttpParam.DATE, date)
            }
        }.body()
    }
}