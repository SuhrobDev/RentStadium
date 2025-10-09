package dev.soul.data.remote.datasource.user.like_share

import dev.soul.data.remote.dto.user.like.request.LikeRequest
import dev.soul.data.remote.dto.user.like.response.LikedItemDto
import dev.soul.data.remote.dto.user.search.response.StadiumItemResponse
import dev.soul.domain.model.PagingModel
import dev.soul.network.HttpParam
import dev.soul.network.HttpRoutes
import dev.soul.network.MainResponse
import dev.soul.network.PagingResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class LikeShareDatasourceImpl(
    private val httpClient: HttpClient
) : LikeShareDatasource {

    override suspend fun like(body: LikeRequest): MainResponse<StadiumItemResponse> {
        return httpClient.post(HttpRoutes.LIKE) {
            setBody(body)
        }.body()
    }

    override suspend fun deleteLike(id: Int): Unit {
        return httpClient.delete(HttpRoutes.LIKE + "$id/").body()
    }

    override suspend fun likes(
        page: Int,
        size: Int
    ): MainResponse<PagingResponse<LikedItemDto>> {
        return httpClient.get(HttpRoutes.LIKE) {
            parameter(HttpParam.PAGE, page)
            parameter(HttpParam.PAGE_SIZE, size)
        }.body()
    }
}