package dev.soul.data.repository.user

import dev.soul.data.mapper.toModel
import dev.soul.data.remote.datasource.user.search.SearchDatasource
import dev.soul.datastore.datastore.DataStoreRepository
import dev.soul.domain.model.PagingModel
import dev.soul.domain.model.user.search.response.StadiumItemModel
import dev.soul.domain.repository.user.SearchRepository
import dev.soul.network.PagingResponse
import dev.soul.network.ResponseHandler
import dev.soul.shared.utils.Dispatcher
import dev.soul.shared.utils.Logger
import dev.soul.shared.utils.NetworkError
import dev.soul.shared.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchRepositoryImpl(
    private val datasource: SearchDatasource,
    private val datastore: DataStoreRepository,
    private val dispatcher: Dispatcher,
) : SearchRepository, ResponseHandler(datastore) {

    val TAG = "SearchRepositoryImpl"
    override suspend fun searchStadium(
        page: Int,
        size: Int,
        name: String?,
        lat: Double,
        lng: Double,
        type: String?,
        maxPrice: Double?,
        minPrice: Double?,
        maxRate: Double?,
        minRate: Double?,
    ): Flow<Resource<PagingModel<StadiumItemModel>>> = flow {
        try {
            Logger.log(
                TAG,
                "Fetching gifts for page: $page, limit: $size"
            )

            val response = datasource.searchStadium(
                page = page,
                size = size,
                name = name,
                lat = lat,
                lng = lng,
                type = type,
                maxPrice = maxPrice,
                minPrice = minPrice,
                maxRate = maxRate,
                minRate = minRate
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