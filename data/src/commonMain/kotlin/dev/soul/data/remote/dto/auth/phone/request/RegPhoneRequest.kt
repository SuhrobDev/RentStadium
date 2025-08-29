package dev.soul.data.remote.dto.auth.phone.request

import kotlinx.serialization.Serializable

@Serializable
data class RegPhoneRequest(
    val phone_number: String,
)
