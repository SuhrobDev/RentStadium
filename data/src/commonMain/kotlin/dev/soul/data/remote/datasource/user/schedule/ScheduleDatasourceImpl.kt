package dev.soul.data.remote.datasource.user.schedule

import dev.soul.data.remote.dto.user.schedule.response.ScheduleItemDto
import dev.soul.network.HttpParam
import dev.soul.network.HttpRoutes
import dev.soul.network.MainResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ScheduleDatasourceImpl(
    private val httpClient: HttpClient
) : ScheduleDatasource {

    override suspend fun schedule(isHistory: Boolean): MainResponse<List<ScheduleItemDto>> {
        return httpClient.get(HttpRoutes.SCHEDULE) {
            url {
                parameters.append(HttpParam.INCLUDE_PAST, "$isHistory")
            }
        }.body()
    }
}