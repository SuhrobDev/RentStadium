package dev.soul.domain.model.auth.register.request

data class RegisterModel(
    val firstName: String,
    val lastName: String,
    val password: String,
    val phoneNumber: String,
    val repeatPassword: String
)