package dev.soul.user.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.soul.shared.Resources

@Composable
fun HomeStadionItem(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
    ) {
        PagingImage(
            modifier = Modifier.clip(RoundedCornerShape(16.dp)),
            imageList = listOf(
                Resources.Icon.Entry1,
                Resources.Icon.Entry2,
                Resources.Icon.Entry3
            ),
            onLiked = {

            }
        )

        StadionInfo()

        Spacer(modifier = Modifier.height(16.dp))
    }
}