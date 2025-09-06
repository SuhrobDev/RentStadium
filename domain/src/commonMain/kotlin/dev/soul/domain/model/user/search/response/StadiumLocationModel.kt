package dev.soul.domain.model.user.search.response

data class StadiumLocationModel(
    val coordinates: List<Double>,
    val type: String
)