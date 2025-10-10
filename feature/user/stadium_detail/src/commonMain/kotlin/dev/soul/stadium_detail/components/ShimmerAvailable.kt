package dev.soul.stadium_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.soul.shared.components.shimmerEffect
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun ShimmerAvailable(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = CustomThemeManager.colors.baseScreenBackground
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        border = CardDefaults.outlinedCardBorder(false)
            .copy(width = 0.dp, brush = Brush.linearGradient(listOf(Color.Gray, Color.Gray)))
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentWidth().padding(vertical = 8.dp, horizontal = 6.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Time text shimmer (matches "startTime-endTime" text)
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect(isLoading = true)
            )

//            // Person icon row shimmer
//            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
//                Box(
//                    modifier = Modifier
//                        .size(12.dp)
//                        .clip(RoundedCornerShape(2.dp))
//                        .shimmerEffect(isLoading = true)
//                )
//            }
        }
    }
}