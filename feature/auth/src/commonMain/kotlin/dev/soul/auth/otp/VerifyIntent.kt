package dev.soul.auth.otp

sealed interface VerifyIntent {
    data class OnEnterNumber(val number: Int?, val index: Int): VerifyIntent
    data class OnChangeFieldFocused(val index: Int): VerifyIntent
    data object OnKeyboardBack: VerifyIntent
    data object OnVerified: VerifyIntent
    data object OnRestartTimer: VerifyIntent

    data class OnGetOtp(val phoneNumber: String): VerifyIntent

    data object OnResendOtp: VerifyIntent

    data class OnVerifyOtp(val otp: String): VerifyIntent

    data object ClearState: VerifyIntent

    data object Back: VerifyIntent

}