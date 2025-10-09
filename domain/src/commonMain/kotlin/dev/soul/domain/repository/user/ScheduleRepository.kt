package dev.soul.domain.repository.user

import dev.soul.domain.model.user.schedule.response.ScheduleItemModel
import dev.soul.shared.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun schedule(isHistory: Boolean): Flow<Resource<List<ScheduleItemModel>>>
}