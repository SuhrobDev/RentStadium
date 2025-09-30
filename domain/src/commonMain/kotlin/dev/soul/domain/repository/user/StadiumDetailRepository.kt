package dev.soul.domain.repository.user

import dev.soul.domain.model.user.stadium_detail.response.StadiumDetailModel
import dev.soul.shared.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StadiumDetailRepository {
    suspend fun stadiumDetail(
        id: Int
    ): Flow<Resource<StadiumDetailModel>>

}