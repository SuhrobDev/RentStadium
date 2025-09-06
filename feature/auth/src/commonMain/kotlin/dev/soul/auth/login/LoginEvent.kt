package dev.soul.auth.login

sealed interface LoginEvent {
    data class PhoneChanged(val phone: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
    object Login : LoginEvent
    object Register : LoginEvent

}