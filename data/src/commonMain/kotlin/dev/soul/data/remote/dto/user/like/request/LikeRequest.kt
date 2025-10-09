package dev.soul.data.remote.dto.user.like.request

import kotlinx.serialization.Serializable

@Serializable
data class LikeRequest(
    val stadium_id: Int
)
