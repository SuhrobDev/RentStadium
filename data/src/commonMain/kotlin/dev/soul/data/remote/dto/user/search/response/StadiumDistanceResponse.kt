package dev.soul.data.remote.dto.user.search.response

import kotlinx.serialization.Serializable

@Serializable
data class StadiumDistanceResponse(
    val unit: String? = null,
    val value: Double? = null
)