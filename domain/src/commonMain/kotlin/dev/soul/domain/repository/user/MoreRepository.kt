package dev.soul.domain.repository.user

import dev.soul.domain.model.PagingModel
import dev.soul.domain.model.user.search.response.StadiumItemModel
import dev.soul.shared.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MoreRepository {
    suspend fun personalized(
        page: Int,
        size: Int,
    ): Flow<Resource<PagingModel<StadiumItemModel>>>

    suspend fun popular(
        lat: Double,
        lng: Double,
        page: Int,
        size: Int,
    ): Flow<Resource<PagingModel<StadiumItemModel>>>
}