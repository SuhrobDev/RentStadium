package dev.soul.data.remote.dto.auth.login.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val access: String,
    val refresh: String
)