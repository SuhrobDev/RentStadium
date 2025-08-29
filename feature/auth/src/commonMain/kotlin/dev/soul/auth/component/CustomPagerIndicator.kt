package dev.soul.auth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomPagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    pageCount: Int,
    activeColor: Color = Color.Black,
    inactiveColor: Color = Color.LightGray,
    indicatorWidth: Dp = 12.dp,
    indicatorHeight: Dp = 4.dp,
    spacing: Dp = 8.dp,
    shape: Shape = RoundedCornerShape(50) // use RectangleShape for sharp edges
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .width(indicatorWidth)
                    .height(indicatorHeight)
                    .clip(shape)
                    .background(if (pagerState.currentPage == index) activeColor else inactiveColor)
            )
        }
    }
}