package dev.soul.domain.model.user.stadium_detail.response

data class StadiumDetailModel(
    val address: String,
    val comments: List<CommentModel>,
    val distance: String,
    val id: Int,
    val images: List<ImageModel>,
    val liked: String,
    val location: LocationModel,
    val name: String,
    val price: String,
    val rating: String
)