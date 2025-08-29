package dev.soul.domain.model.auth.verify.request

data class VerifyOtpModel(
    val phoneNumber: String,
    val otp: String
)
