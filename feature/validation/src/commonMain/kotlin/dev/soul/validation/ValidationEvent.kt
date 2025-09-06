package dev.soul.validation

sealed interface ValidationEvent {
    data class ShowLogout(val show: Boolean) : ValidationEvent

}