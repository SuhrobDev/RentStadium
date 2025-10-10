package dev.soul.domain.repository.user

import dev.soul.domain.model.user.available.response.AvailableModel
import dev.soul.domain.model.user.book.request.BookRequestModel
import dev.soul.domain.model.user.book.response.BookResponseModel
import dev.soul.domain.model.user.stadium_detail.response.StadiumDetailModel
import dev.soul.domain.model.user.upcoming_days.response.UpcomingDaysModel
import dev.soul.shared.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StadiumDetailRepository {
    suspend fun stadiumDetail(
        id: Int
    ): Flow<Resource<StadiumDetailModel>>

    suspend fun upcomingDays() : Flow<Resource<List<UpcomingDaysModel>>>

    suspend fun available(id: Int, date: String) : Flow<Resource<List<AvailableModel>>>

    suspend fun book(body: List<BookRequestModel>): Flow<Resource<List<BookResponseModel>>>

}