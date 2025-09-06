package dev.soul.auth.register_phone

import androidx.compose.runtime.Immutable

@Immutable
data class RegisterPhoneState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val enableButton: Boolean = false,
    val errorMessage: String? = null,
    val phone:String = "",
)
