package dev.soul.auth.register_info

sealed interface RegisterEvent {
    data class PhoneChanged(val phone: String) : RegisterEvent
    data class FullNameChanged(val firstName: String) : RegisterEvent
    data class PasswordChanged(val password: String) : RegisterEvent
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterEvent
    object Register : RegisterEvent
}