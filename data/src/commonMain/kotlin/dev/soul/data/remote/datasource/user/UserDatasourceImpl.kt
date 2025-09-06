package dev.soul.data.remote.datasource.user

import dev.soul.data.remote.dto.auth.login.response.LoginResponse
import dev.soul.data.remote.dto.refresh.request.RefreshRequest
import dev.soul.data.remote.dto.user.response.UserResponse
import dev.soul.network.HttpRoutes
import dev.soul.network.MainResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class UserDatasourceImpl(
    private val httpClient: HttpClient
) : UserDatasource {

    override suspend fun getUser(): MainResponse<UserResponse> {
        return httpClient.get(HttpRoutes.PROFILE).body()
    }

    override suspend fun refreshToken(refresh: String): MainResponse<LoginResponse> {
        return httpClient.post(HttpRoutes.REFRESH_TOKEN) {
            setBody(RefreshRequest(refresh))
        }.body()
    }
}