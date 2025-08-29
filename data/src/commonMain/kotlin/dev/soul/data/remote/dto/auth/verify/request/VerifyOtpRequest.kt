package dev.soul.data.remote.dto.auth.verify.request

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpRequest(
    val phone_number: String,
    val otp: String
)
