package dev.soul.auth.register_info

import androidx.compose.runtime.Immutable

@Immutable
data class RegisterState(
    val phone: String = "",
    val firstName:String="",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isVerified: Boolean = false,
    val error: String? = null
)
