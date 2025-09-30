package dev.soul.data.remote.datasource.user.stadium_detail

import dev.soul.data.remote.dto.user.stadium_detail.response.StadiumDetailResponse
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
}