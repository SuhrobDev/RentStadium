package dev.soul.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val errorMessage: String? = null
)