package dev.soul.data.remote.dto.auth.register.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val first_name: String,
    val last_name: String,
    val password: String,
    val phone_number: String,
    val repeat_password: String
)