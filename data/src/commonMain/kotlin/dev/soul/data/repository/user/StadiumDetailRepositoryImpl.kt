package dev.soul.data.repository.user

import dev.soul.data.mapper.toModel
import dev.soul.data.remote.datasource.user.stadium_detail.StadiumDetailDatasource
import dev.soul.datastore.datastore.DataStoreRepository
import dev.soul.domain.model.user.stadium_detail.response.StadiumDetailModel
import dev.soul.domain.repository.user.StadiumDetailRepository
import dev.soul.network.ResponseHandler
import dev.soul.shared.utils.Dispatcher
import dev.soul.shared.utils.NetworkError
import dev.soul.shared.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class StadiumDetailRepositoryImpl(
    private val datasource: StadiumDetailDatasource,
    private val datastore: DataStoreRepository,
    private val dispatcher: Dispatcher,
) : StadiumDetailRepository, ResponseHandler(datastore) {

    override suspend fun stadiumDetail(id: Int): Flow<Resource<StadiumDetailModel>> = loadResult({
        datasource.stadiumDetail(id)
    }, { data, flow ->
        try {
            data.data?.let {
                flow.emit(
                    Resource.Success(
                        it.toModel()
                    )
                )
            } ?: runCatching {
                flow.emit(
                    Resource.Error(message = NetworkError.NULL_BODY)
                )
            }
        } catch (e: Exception) {
            flow.emit(Resource.Error(NetworkError.SERIALIZATION))
        }
    }).flowOn(dispatcher.io)

}