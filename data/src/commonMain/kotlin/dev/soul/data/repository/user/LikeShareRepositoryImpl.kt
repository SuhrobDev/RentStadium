package dev.soul.data.repository.user

import dev.soul.data.mapper.toModel
import dev.soul.data.remote.datasource.user.home.HomeDatasource
import dev.soul.data.remote.datasource.user.like_share.LikeShareDatasource
import dev.soul.data.remote.dto.user.like.request.LikeRequest
import dev.soul.datastore.datastore.DataStoreRepository
import dev.soul.domain.model.PagingModel
import dev.soul.domain.model.user.search.response.StadiumItemModel
import dev.soul.domain.repository.user.LikeShareRepository
import dev.soul.network.MainResponse
import dev.soul.network.ResponseHandler
import dev.soul.shared.Resources
import dev.soul.shared.utils.Dispatcher
import dev.soul.shared.utils.Logger
import dev.soul.shared.utils.NetworkError
import dev.soul.shared.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LikeShareRepositoryImpl(
    private val datasource: LikeShareDatasource,
    private val datastore: DataStoreRepository,
    private val dispatcher: Dispatcher,
): LikeShareRepository, ResponseHandler(datastore) {

    val TAG = "LikeShareRepositoryImpl"
    override suspend fun like(id: Int): Flow<Resource<StadiumItemModel>> =
        loadResult({
        datasource.like(
            LikeRequest(id)
        )
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

    override suspend fun deleteLiked(id: Int): Flow<Resource<Unit>> =
        loadResult({
            datasource.deleteLike(id)
        }, { data, flow ->
            try {
                flow.emit(
                    Resource.Success(
                        Unit
                    )
                )
            } catch (e: Exception) {
                flow.emit(Resource.Error(NetworkError.SERIALIZATION))
            }
        }).flowOn(dispatcher.io)

    override suspend fun likes(
        page: Int,
        size: Int,
        lat: Double,
        lng: Double
    ): Flow<Resource<PagingModel<StadiumItemModel>>> = flow {
        try {
            val response = datasource.likes(
                page = page,
                size = size,
            )

            Logger.log(TAG, "Raw response: $response")

            when {
                response.data != null -> {
                    val content = response.data!!
                    val meta = response.data

                    Logger.log(TAG, "Content size: ${content.results?.size}, Meta: $meta")
                    val pagingResponse = PagingModel(
                        results = content.results?.map { it.toModel() },
                        count = meta?.count,
                        next = meta?.next,
                        previous = meta?.previous,
                    )
                    emit(Resource.Success(pagingResponse))
                }

                response.message != null -> {
                    Logger.log(TAG, "API Error: ${response.message}")
                    emit(
                        Resource.Error(
                            NetworkError.valueOf(
                                response.message ?: NetworkError.UNKNOWN_ERROR.name
                            )
                        )
                    )
                }

                else -> {
                    Logger.log(TAG, "Unknown error - no data and no error message")
                    emit(Resource.Error(NetworkError.UNKNOWN_ERROR))
                }
            }
        } catch (e: Exception) {
            Logger.log(TAG, "Exception: $e")
            Logger.log(TAG, "Exception caught: ${e.message}")
            Logger.log(TAG, "Exception type: ${e::class.simpleName}")
            e.printStackTrace()
            emit(Resource.Error(NetworkError.NETWORK_ERROR))
        }
    }.flowOn(dispatcher.io)
}