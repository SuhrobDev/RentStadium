package dev.soul.data.remote.dto.user.stadium_detail.response

import dev.soul.data.remote.dto.user.response.UserResponse
import kotlinx.serialization.Serializable

@Serializable
data class CommentResponse(
    val created: String? = null,
    val id: Int? = null,
    val message: String? = null,
    val modified: String? = null,
    val rating: Int? = null,
    val stadium: Int? = null,
    val user: UserResponse?=null
)