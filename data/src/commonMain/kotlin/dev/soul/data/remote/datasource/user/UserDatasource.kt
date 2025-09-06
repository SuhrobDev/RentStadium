package dev.soul.data.remote.datasource.user

import dev.soul.data.remote.dto.auth.login.response.LoginResponse
import dev.soul.data.remote.dto.user.response.UserResponse
import dev.soul.network.MainResponse

interface UserDatasource {
    suspend fun getUser(): MainResponse<UserResponse>

    suspend fun refreshToken(refresh: String): MainResponse<LoginResponse>
}