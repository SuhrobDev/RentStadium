package dev.soul.user.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.soul.domain.model.user.search.response.StadiumItemModel

@Composable
fun HomeStadionItem(
    modifier: Modifier = Modifier,
    stadium: StadiumItemModel,
    onClick: (Int) -> Unit,
    onLiked: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick(stadium.id)
            }
    ) {
        PagingImage(
            modifier = Modifier.clip(RoundedCornerShape(16.dp)),
            imageList = stadium.images,
            onLiked = {
                onLiked(stadium.id)
            }
        )

        StadionInfo(info = stadium)

        Spacer(modifier = Modifier.height(16.dp))
    }
}