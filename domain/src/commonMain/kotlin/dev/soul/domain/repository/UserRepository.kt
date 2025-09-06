package dev.soul.domain.repository

import dev.soul.domain.model.auth.login.response.LoginResponseModel
import dev.soul.domain.model.user.response.UserModel
import dev.soul.shared.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUser(firstLogin:()-> Unit): Flow<Resource<UserModel>>

    suspend fun refreshToken(): Flow<Resource<LoginResponseModel>>
}