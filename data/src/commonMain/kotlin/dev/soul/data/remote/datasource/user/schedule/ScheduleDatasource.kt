package dev.soul.data.remote.datasource.user.schedule

import dev.soul.data.remote.dto.user.schedule.response.ScheduleItemDto
import dev.soul.network.MainResponse

interface ScheduleDatasource {
    suspend fun schedule(isHistory: Boolean): MainResponse<List<ScheduleItemDto>>
}