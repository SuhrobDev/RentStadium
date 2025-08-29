package dev.soul.domain.model.auth.login.response

data class LoginResponseModel(
    val access: String,
    val refresh: String
)