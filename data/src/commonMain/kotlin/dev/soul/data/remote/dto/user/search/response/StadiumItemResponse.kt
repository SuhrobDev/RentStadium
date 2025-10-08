package dev.soul.data.remote.dto.user.search.response

import dev.soul.data.remote.dto.user.StadiumDistanceResponse
import dev.soul.data.remote.dto.user.StadiumImageResponse
import dev.soul.data.remote.dto.user.StadiumLocationResponse
import kotlinx.serialization.Serializable

@Serializable
data class StadiumItemResponse(
    val address: String? = null,
    val distance: StadiumDistanceResponse? = null,
    val id: Int? = null,
    val images: List<StadiumImageResponse>? = emptyList(),
    val liked: Boolean? = null,
    val location: StadiumLocationResponse? = null,
    val name: String? = null,
    val price: String? = null,
    val rating: String? = null
)