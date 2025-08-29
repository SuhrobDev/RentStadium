package dev.soul.domain.model.auth.login.request

data class LoginRequestModel(
    val phoneNumber: String,
    val password: String,
)