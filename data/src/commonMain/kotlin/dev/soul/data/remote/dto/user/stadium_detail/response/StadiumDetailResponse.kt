package dev.soul.data.remote.dto.user.stadium_detail.response

import kotlinx.serialization.Serializable

@Serializable
data class StadiumDetailResponse(
    val address: String? = null,
    val comments: List<CommentResponse>? = emptyList(),
    val distance: String? = null,
    val id: Int? = null,
    val images: List<ImageResponse>? = emptyList(),
    val liked: String? = null,
    val location: LocationResponse? = null,
    val name: String? = null,
    val price: String? = null,
    val rating: String? = null
)