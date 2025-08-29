package dev.soul.data.repository

import dev.soul.data.mapper.toDto
import dev.soul.data.mapper.toModel
import dev.soul.data.remote.datasource.auth.AuthDatasource
import dev.soul.datastore.datastore.DataStoreRepository
import dev.soul.datastore.datastore.PreferencesKeys
import dev.soul.domain.model.auth.login.request.LoginRequestModel
import dev.soul.domain.model.auth.login.response.LoginResponseModel
import dev.soul.domain.model.auth.phone.request.RegPhoneModel
import dev.soul.domain.model.auth.register.request.RegisterModel
import dev.soul.domain.model.auth.verify.request.VerifyOtpModel
import dev.soul.domain.repository.AuthRepository
import dev.soul.network.NetworkError
import dev.soul.network.Resource
import dev.soul.network.ResponseHandler
import dev.soul.shared.utils.DispatchersProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class AuthRepositoryImpl(
    private val datasource: AuthDatasource,
    private val datastore: DataStoreRepository,
    private val dispatcher: DispatchersProvider,
) : AuthRepository, ResponseHandler(datastore) {

    override suspend fun setLanguage(language: String) =
        datastore.saveData(PreferencesKeys.LANGUAGE, language)

    override suspend fun login(loginRequest: LoginRequestModel): Flow<Resource<LoginResponseModel>> =
        loadResult({
            datasource.login(loginRequest.toDto())
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
        }, forLogin = true).flowOn(dispatcher.io)

    override suspend fun getOtp(phone: RegPhoneModel): Flow<Resource<Unit>> = loadResult({
        datasource.getOtp(phone.toDto())
    }, { data, flow ->
        try {
            flow.emit(Resource.Success(Unit))
        } catch (e: Exception) {
            flow.emit(Resource.Error(NetworkError.SERIALIZATION))
        }
    }).flowOn(dispatcher.io)

    override suspend fun verifyOtp(verify: VerifyOtpModel): Flow<Resource<Unit>> =
        loadResult({
            datasource.verifyOtp(verify.toDto())
        }, { data, flow ->
            try {
                flow.emit(Resource.Success(Unit))
            } catch (e: Exception) {
                flow.emit(Resource.Error(NetworkError.SERIALIZATION))
            }
        }).flowOn(dispatcher.io)

    override suspend fun register(
        body: RegisterModel
    ): Flow<Resource<Unit>> =
        loadResult({
            datasource.register(
                body.toDto()
            )
        }, { data, flow ->
            try {
                flow.emit(Resource.Success(Unit))
            } catch (e: Exception) {
                flow.emit(Resource.Error(NetworkError.SERIALIZATION))
            }
        }).flowOn(dispatcher.io)
}