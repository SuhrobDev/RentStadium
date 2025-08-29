package dev.soul.network

import dev.soul.datastore.datastore.DataStoreRepository
import dev.soul.shared.utils.Logger
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import okio.IOException

private const val TAG = "ResponseHandler"

open class ResponseHandler(
    private val dataStore: DataStoreRepository
) {
    protected fun <T, K> loadResult(
        requestSource: suspend () -> T,
        successBody: suspend (T, FlowCollector<Resource<K>>) -> Unit,
        forLogin: Boolean = false,
        offlineMode: suspend (FlowCollector<Resource<K>>) -> Unit = { }
    ): Flow<Resource<K>> =
        flow {
            try {
                val data = requestSource.invoke()
                Logger.log(TAG, "loadResult: $data")
                try {
                    successBody.invoke(data, this)
                    Logger.log(TAG, "Success: ${data}")

                } catch (e: Exception) {
                    Logger.logError(TAG, "Error: ${e.message}")
                    emit(Resource.Error(NetworkError.SERIALIZATION))
                }

            } catch (e: RedirectResponseException) {
                Logger.logError(TAG, "3XX Error: ${e.response.call.response.status.description}")
                emit(Resource.Error(NetworkError.REDIRECT))
            } catch (e: ClientRequestException) {
                val rawErrorBody = try {
                    e.response.bodyAsText() // get raw string (won't throw)
                } catch (ex: Exception) {
                    ""
                }

                val errorMessage = try {
                    val error = Json.decodeFromString<ApiError>(e.response.bodyAsText())
                    error.errorMessage
                } catch (ex: Exception) {
                    Logger.logError(TAG, "JSON parse error: ${ex.message}")
                    "Unknown error occurred"
                }

                Logger.logError(TAG, "${e.response.status.value} Error raw body: $rawErrorBody")

                when (e.response.status.value) {
                    401 -> {
                        dataStore.clear()
                        emit(Resource.Error(NetworkError.UNAUTHORIZED))
                    }

                    404 -> {
                        if (forLogin) {
                            emit(Resource.Error(NetworkError.NOT_FOUND))
                        } else {
                            emit(
                                Resource.Error(
                                    NetworkError.SERVER_CUSTOM_ERROR.apply {
                                        message = errorMessage
                                    }
                                )
                            )
                        }
                    }

                    else -> {
                        emit(Resource.Error(NetworkError.SERVER_CUSTOM_ERROR.apply {
                            message = errorMessage
                        }))
                    }
                }
            } catch (e: ServerResponseException) {
                Logger.logError(TAG, "5xx Error: ${e.response.status.description}")
                emit(Resource.Error(NetworkError.SERVER_NOT_RESPONDING.apply {
                    message = e.response.body<ApiError>().errorMessage
                }))
            } catch (e: IOException) {
                offlineMode.invoke(this)
                Logger.logError(TAG, "IOException Server unreachable: ${e.message}")
                emit(Resource.Error(NetworkError.NO_INTERNET))
            } catch (e: Exception) {
                Logger.logError(TAG, "Exception Error: ${e.message}")
                emit(Resource.Error(NetworkError.UNKNOWN))
            }
        }
}
