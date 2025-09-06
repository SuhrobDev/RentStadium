package dev.soul.domain.model.user.search.response

data class StadiumItemModel(
    val address: String,
    val distance: StadiumDistanceModel,
    val id: Int,
    val images: List<StadiumImageModel>,
    val liked: Boolean,
    val location: StadiumLocationModel,
    val name: String,
    val price: String,
    val rating: String
)