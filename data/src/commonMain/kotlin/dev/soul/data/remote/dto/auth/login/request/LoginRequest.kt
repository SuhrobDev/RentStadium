package dev.soul.data.remote.dto.auth.login.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val phone_number: String,
    val password: String,
)