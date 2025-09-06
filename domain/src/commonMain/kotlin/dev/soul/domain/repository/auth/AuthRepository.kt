package dev.soul.domain.repository.auth

import dev.soul.domain.model.auth.login.request.LoginRequestModel
import dev.soul.domain.model.auth.login.response.LoginResponseModel
import dev.soul.domain.model.auth.phone.request.RegPhoneModel
import dev.soul.domain.model.auth.register.request.RegisterModel
import dev.soul.domain.model.auth.verify.request.VerifyOtpModel
import dev.soul.shared.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun setLanguage(language: String)

    suspend fun login(loginRequest: LoginRequestModel): Flow<Resource<LoginResponseModel>>

    suspend fun getOtp(phone: RegPhoneModel): Flow<Resource<Unit>>

    suspend fun verifyOtp(verifyOtpRequestDto: VerifyOtpModel): Flow<Resource<Unit>>

    suspend fun register(body: RegisterModel): Flow<Resource<Unit>>
}