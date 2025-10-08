package dev.soul.data.remote.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class StadiumLocationResponse(
    val coordinates: List<Double>? = emptyList(),
    val type: String? = null
)