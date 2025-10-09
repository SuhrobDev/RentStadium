package dev.soul.domain.repository.user

import dev.soul.domain.model.PagingModel
import dev.soul.domain.model.user.search.response.StadiumItemModel
import dev.soul.network.MainResponse
import dev.soul.shared.Resources
import dev.soul.shared.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LikeShareRepository {
    suspend fun like(id: Int): Flow<Resource<StadiumItemModel>>
    suspend fun deleteLiked(id: Int): Flow<Resource<Unit>>

    suspend fun likes(page: Int, size: Int, lat: Double, lng: Double): Flow<Resource<PagingModel<StadiumItemModel>>>

}