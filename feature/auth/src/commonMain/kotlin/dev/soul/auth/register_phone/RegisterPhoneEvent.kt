package dev.soul.auth.register_phone

sealed interface RegisterPhoneEvent {
    data class PhoneChanged(val phone: String) : RegisterPhoneEvent
    object Register : RegisterPhoneEvent
}