package dev.soul.domain.model.user.stadium_detail.response

import dev.soul.domain.model.user.response.UserModel

data class CommentModel(
    val created: String,
    val id: Int,
    val message: String,
    val modified: String,
    val rating: Int,
    val stadium: Int,
    val user: UserModel
)