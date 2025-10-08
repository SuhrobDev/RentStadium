package dev.soul.data.remote.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class StadiumDistanceResponse(
    val unit: String? = null,
    val value: Double? = null
)