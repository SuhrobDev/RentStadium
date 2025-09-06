package dev.soul.data.remote.dto.user.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val date_joined: String? = null,
    val email: String? = null,
    val first_name: String? = null,
    val last_name: String? = null,
    val id: Int? = null,
    val is_active: Boolean? = null,
    val last_login: String? = null,
    val phone_number: String? = null,
)