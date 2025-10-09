package dev.soul.data.remote.dto.user.like.response

import dev.soul.data.remote.dto.user.search.response.StadiumItemResponse
import kotlinx.serialization.Serializable

@Serializable
data class LikedItemDto(
    val created: String? = null,
    val id: Int? = null,
    val modified: String? = null,
    val stadium: StadiumItemResponse? = null
)