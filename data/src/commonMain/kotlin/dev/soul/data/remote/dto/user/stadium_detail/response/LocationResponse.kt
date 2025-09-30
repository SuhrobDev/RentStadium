package dev.soul.data.remote.dto.user.stadium_detail.response

import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    val coordinates: List<Double>? = emptyList(),
    val type: String? = null
)