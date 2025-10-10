package dev.soul.domain.model.user.book.request

import dev.soul.domain.model.user.schedule.response.DatetimeRangeModel

data class BookRequestModel(
    val datetimeRange: DatetimeRangeModel,
    val note: String,
    val stadium: Int,
    val status: String
)
