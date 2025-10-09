package dev.soul.domain.model.user.available.response

data class AvailableModel(
    val created: String,
    val dayOfWeek: Int,
    val dayOfWeekDisplay: String,
    val endTime: String,
    val id: Int,
    val isActive: Boolean,
    val isBooked: Boolean,
    val modified: String,
    val price: String,
    val stadium: Int,
    val startTime: String
)
