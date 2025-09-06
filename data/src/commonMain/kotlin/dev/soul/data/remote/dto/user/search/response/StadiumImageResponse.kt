package dev.soul.data.remote.dto.user.search.response

import kotlinx.serialization.Serializable

@Serializable
data class StadiumImageResponse(
    val image: String? = null,
    val name: String? = null,
    val uuid: String? = null
)