package dev.soul.data.remote.datasource.auth

import dev.soul.data.remote.dto.auth.register.request.RegisterRequest
import dev.soul.data.remote.dto.auth.register.response.RegisterResponse
import dev.soul.data.remote.dto.auth.login.request.LoginRequest
import dev.soul.data.remote.dto.auth.login.response.LoginResponse
import dev.soul.data.remote.dto.auth.phone.request.RegPhoneRequest
import dev.soul.data.remote.dto.auth.verify.request.VerifyOtpRequest
import dev.soul.network.MainResponse

interface AuthDatasource {
    suspend fun login(loginRequestDto: LoginRequest): MainResponse<LoginResponse>

    suspend fun getOtp(phoneNumber: RegPhoneRequest): MainResponse<Unit>

    suspend fun verifyOtp(verifyOtpRequestDto: VerifyOtpRequest): MainResponse<Unit>

    suspend fun register(requestDto: RegisterRequest): MainResponse<RegisterResponse>
}