package dev.soul.data.remote.datasource.user.stadium_detail

import dev.soul.data.remote.dto.user.available.response.AvailableDto
import dev.soul.data.remote.dto.user.book.request.BookRequestDto
import dev.soul.data.remote.dto.user.book.response.BookResponseDto
import dev.soul.data.remote.dto.user.schedule.detail.response.ScheduleDetailDto
import dev.soul.data.remote.dto.user.stadium_detail.response.StadiumDetailResponse
import dev.soul.data.remote.dto.user.upcoming_days.response.UpcomingDaysDto
import dev.soul.network.MainResponse

interface StadiumDetailDatasource {
    suspend fun stadiumDetail(
        id: Int
    ): MainResponse<StadiumDetailResponse>

    suspend fun upcomingDays(): MainResponse<List<UpcomingDaysDto>>

    suspend fun available(id: Int, date: String): MainResponse<List<AvailableDto>>
    suspend fun book(body: List<BookRequestDto>): MainResponse<List<BookResponseDto>>

    suspend fun scheduleDetail(
        id: Int
    ): ScheduleDetailDto

    suspend fun deleteSchedule(id: Int): Unit
}