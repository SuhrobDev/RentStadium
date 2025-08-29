package dev.soul.data.remote.datasource.auth

import dev.soul.data.remote.dto.auth.register.request.RegisterRequest
import dev.soul.data.remote.dto.auth.register.response.RegisterResponse
import dev.soul.data.remote.dto.auth.login.request.LoginRequest
import dev.soul.data.remote.dto.auth.login.response.LoginResponse
import dev.soul.data.remote.dto.auth.phone.request.RegPhoneRequest
import dev.soul.data.remote.dto.auth.verify.request.VerifyOtpRequest
import dev.soul.network.HttpRoutes
import dev.soul.network.MainResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AuthDatasourceImpl(
    private val httpClient: HttpClient
) : AuthDatasource {

    override suspend fun login(loginRequestDto: LoginRequest): MainResponse<LoginResponse> {
        return httpClient.post(HttpRoutes.LOGIN) {
            setBody(loginRequestDto)
        }.body()
    }

    override suspend fun getOtp(phoneNumber: RegPhoneRequest): MainResponse<Unit> {
        return httpClient.post(HttpRoutes.PHONE) {
            setBody(phoneNumber)
        }.body()
    }

    override suspend fun verifyOtp(verifyOtpRequestDto: VerifyOtpRequest): MainResponse<Unit> {
        return httpClient.post(HttpRoutes.VERIFY_OTP) {
            setBody(verifyOtpRequestDto)
        }.body()
    }

    override suspend fun register(requestDto: RegisterRequest): MainResponse<RegisterResponse> {
        return httpClient.post(HttpRoutes.REGISTER) {
            setBody(requestDto)
        }.body()
    }
}