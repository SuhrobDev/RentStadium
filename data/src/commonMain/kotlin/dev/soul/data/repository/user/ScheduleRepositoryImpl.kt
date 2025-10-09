package dev.soul.data.repository.user

import dev.soul.data.mapper.toModel
import dev.soul.data.remote.datasource.user.schedule.ScheduleDatasource
import dev.soul.datastore.datastore.DataStoreRepository
import dev.soul.domain.model.user.schedule.response.ScheduleItemModel
import dev.soul.domain.repository.user.ScheduleRepository
import dev.soul.network.ResponseHandler
import dev.soul.shared.utils.Dispatcher
import dev.soul.shared.utils.NetworkError
import dev.soul.shared.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ScheduleRepositoryImpl(
    private val datasource: ScheduleDatasource,
    private val datastore: DataStoreRepository,
    private val dispatcher: Dispatcher,
) : ScheduleRepository, ResponseHandler(datastore) {

    override suspend fun schedule(isHistory: Boolean): Flow<Resource<List<ScheduleItemModel>>> =
        loadResult({
            datasource.schedule(isHistory)
        }, { data, flow ->
            try {
                data.data?.let {
                    flow.emit(
                        Resource.Success(
                            it.map { it.toModel() }
                        )
                    )
                } ?: runCatching {
                    flow.emit(
                        Resource.Error(
                            message = NetworkError.SERVER_CUSTOM_ERROR.apply {
                                message = data.error ?: "Error on loading personalized"
                            }
                        )
                    )
                }
            } catch (e: Exception) {
                flow.emit(Resource.Error(NetworkError.SERIALIZATION))
            }
        }).flowOn(dispatcher.io)

}