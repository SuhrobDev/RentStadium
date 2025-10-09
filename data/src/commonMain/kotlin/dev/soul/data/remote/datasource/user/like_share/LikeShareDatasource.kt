package dev.soul.data.remote.datasource.user.like_share

import dev.soul.data.remote.dto.user.like.request.LikeRequest
import dev.soul.data.remote.dto.user.like.response.LikedItemDto
import dev.soul.data.remote.dto.user.search.response.StadiumItemResponse
import dev.soul.domain.model.PagingModel
import dev.soul.domain.model.user.search.response.StadiumItemModel
import dev.soul.network.MainResponse
import dev.soul.network.PagingResponse

interface LikeShareDatasource {
    suspend fun like(body: LikeRequest): MainResponse<StadiumItemResponse>
    suspend fun deleteLike(id:Int): Unit
    suspend fun likes(page: Int, size: Int): MainResponse<PagingResponse<LikedItemDto>>
}