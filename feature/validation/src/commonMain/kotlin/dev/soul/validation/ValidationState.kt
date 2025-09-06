package dev.soul.validation

import androidx.compose.runtime.Immutable

@Immutable
data class ValidationState(
    val isLoading: Boolean = false,
    val shouldLogout: Boolean = false,
    val errorMessage: String? = null,
    val everLogger: Boolean = false,
)
