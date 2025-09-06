package dev.soul.data.remote.dto.refresh.request

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequest(
    val refresh: String
)
