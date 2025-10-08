package dev.soul.domain.repository.user

import dev.soul.domain.model.PagingModel
import dev.soul.domain.model.user.search.response.StadiumItemModel
import dev.soul.shared.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun personalized(): Flow<Resource<List<StadiumItemModel>>>

    suspend fun popular(
        lat: Double,
        lng: Double,
    ): Flow<Resource<PagingModel<StadiumItemModel>>>
}