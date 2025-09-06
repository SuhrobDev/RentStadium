package dev.soul.domain.model.user.response

data class UserModel(
    val dateJoined: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val id: Int,
    val isActive: Boolean,
    val lastLogin: String,
    val phoneNumber: String
)