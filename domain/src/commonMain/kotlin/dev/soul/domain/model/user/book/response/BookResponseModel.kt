package dev.soul.domain.model.user.book.response

import dev.soul.domain.model.user.schedule.response.DatetimeRangeModel

data class BookResponseModel(
    val created: String,
    val datetimeRange: DatetimeRangeModel,
    val id: Int,
    val modified: String,
    val notes: String,
    val stadium: Int,
    val status: String
)
