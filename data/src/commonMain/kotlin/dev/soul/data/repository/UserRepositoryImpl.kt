package dev.soul.data.repository

import dev.soul.data.mapper.toModel
import dev.soul.data.remote.datasource.user.UserDatasource
import dev.soul.datastore.datastore.DataStoreRepository
import dev.soul.datastore.datastore.PreferencesKeys
import dev.soul.domain.model.auth.login.response.LoginResponseModel
import dev.soul.domain.model.user.response.UserModel
import dev.soul.domain.repository.UserRepository
import dev.soul.network.ApiError
import dev.soul.network.ResponseHandler
import dev.soul.shared.utils.Dispatcher
import dev.soul.shared.utils.Logger
import dev.soul.shared.utils.NetworkError
import dev.soul.shared.utils.Resource
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

class UserRepositoryImpl(
    private val datasource: UserDatasource,
    private val datastore: DataStoreRepository,
    private val dispatcher: Dispatcher,
) : UserRepository, ResponseHandler(datastore) {

    override suspend fun getUser(firstLogin: () -> Unit): Flow<Resource<UserModel>> = flow {
        val access = datastore.getData(PreferencesKeys.ACCESS_TOKEN, "").first()

        if (access.isEmpty()) {
            firstLogin()
            return@flow
        }

        try {
            val response = datasource.getUser()
            if (response.success == true && response.data != null) {
                response.data?.let {
                    emit(Resource.Success(it.toModel()))
                }
            } else {
                if (response.message?.isNotEmpty() == true && response.message?.contains("token") == true)
                    emit(Resource.Error(NetworkError.UNAUTHORIZED))
                else{
                    emit(Resource.Error(NetworkError.SERVER_CUSTOM_ERROR.apply {
                        message = response.message ?: "Unknown error occurred"
                    }))
                }
            }
        } catch (e: ClientRequestException) {
            when (e.response.status.value) {
                401 -> {
                    emit(Resource.Error(NetworkError.UNAUTHORIZED))
                }

                else -> {
                    val errorMessage = try {
                        val error = Json.decodeFromString<ApiError>(e.response.bodyAsText())
                        error.errorMessage
                    } catch (ex: Exception) {
                        Logger.logError("qwonmjgqw", "JSON parse error: ${ex.message}")
                        "Unknown error occurred"
                    }
                    emit(Resource.Error(NetworkError.SERVER_CUSTOM_ERROR.apply {
                        message = errorMessage
                    }))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(NetworkError.UNKNOWN.apply { message = e.message }))
        }
    }.flowOn(dispatcher.io)

    override suspend fun refreshToken(): Flow<Resource<LoginResponseModel>> = flow {
        val refresh = datastore.getData(PreferencesKeys.REFRESH_TOKEN, "").first()
        val response = datasource.refreshToken(refresh)

        try {
            if (response?.success == true) {
                response.data?.let {
                    datastore.saveData(PreferencesKeys.REFRESH_TOKEN, it.refresh)
                    datastore.saveData(PreferencesKeys.ACCESS_TOKEN, it.access)
                    emit(Resource.Success(it.toModel()))
                } ?: emit(Resource.Error(NetworkError.NULL_BODY))
            }
        } catch (e: ClientRequestException) {
            when (e.response.status.value) {
                401 -> {
                    emit(Resource.Error(NetworkError.UNAUTHORIZED))
                }

                else -> {
                    val errorMessage = try {
                        val error = Json.decodeFromString<ApiError>(e.response.bodyAsText())
                        error.errorMessage
                    } catch (ex: Exception) {
                        Logger.logError("qwonmjgqw", "JSON parse error: ${ex.message}")
                        "Unknown error occurred"
                    }
                    emit(Resource.Error(NetworkError.SERVER_CUSTOM_ERROR.apply {
                        message = errorMessage
                    }))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(NetworkError.UNKNOWN.apply { message = e.message }))
        }
    }.flowOn(dispatcher.io)   // ✅ run API + datastore calls on IO, keep emit safe
}