package dev.soul.domain.repository.user

import dev.soul.domain.model.PagingModel
import dev.soul.domain.model.user.search.response.StadiumItemModel
import dev.soul.network.PagingResponse
import dev.soul.shared.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

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
    ): Flow<Resource<PagingModel<StadiumItemModel>>>

}