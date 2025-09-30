package dev.soul.domain.model.user.search.maps

data class StadiumModel(
    val id: Int,
    val lat: Double,
    val long: Double,
    val name: String,
    val address: String,
    val type: String,
    val price: Double,
    val rate: Double,
    val image: String,
    val distance: Double,
    val isFavorite: Boolean,
)
