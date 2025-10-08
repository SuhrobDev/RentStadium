package dev.soul.data.repository.user

import dev.soul.data.mapper.toModel
import dev.soul.data.remote.datasource.user.more.MoreDatasource
import dev.soul.datastore.datastore.DataStoreRepository
import dev.soul.domain.model.PagingModel
import dev.soul.domain.model.user.search.response.StadiumItemModel
import dev.soul.domain.repository.user.MoreRepository
import dev.soul.network.ResponseHandler
import dev.soul.shared.utils.Dispatcher
import dev.soul.shared.utils.Logger
import dev.soul.shared.utils.NetworkError
import dev.soul.shared.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MoreRepositoryImpl(
    private val datasource: MoreDatasource,
    private val datastore: DataStoreRepository,
    private val dispatcher: Dispatcher,
) : MoreRepository, ResponseHandler(datastore) {
    val TAG = "HomeRepositoryImpl"

    override suspend fun personalized(
        page: Int,
        size: Int
    ): Flow<Resource<PagingModel<StadiumItemModel>>> = flow {
        try {
            val response = datasource.personalized(
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

    override suspend fun popular(
        lat: Double,
        lng: Double,
        page: Int,
        size: Int
    ): Flow<Resource<PagingModel<StadiumItemModel>>> = flow {
        try {
            val response = datasource.popular(
                page = page,
                size = size,
                lat = lat,
                lng = lng,
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