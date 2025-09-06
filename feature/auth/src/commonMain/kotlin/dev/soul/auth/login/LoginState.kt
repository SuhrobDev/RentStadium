package dev.soul.auth.login

import androidx.compose.runtime.Immutable

@Immutable
data class LoginState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val enableLogin: Boolean = false,
    val errorMessage: String? = null,
    val phone:String = "",
    val password:String = ""
)
