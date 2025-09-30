package dev.soul.data.remote.dto.user.stadium_detail.response

import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    val image: String?=null,
    val name: String?=null,
    val uuid: String?=null
)