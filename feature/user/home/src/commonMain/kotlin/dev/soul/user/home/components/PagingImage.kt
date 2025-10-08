package dev.soul.user.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.soul.domain.model.user.search.response.StadiumImageModel
import dev.soul.shared.Resources
import dev.soul.shared.components.CustomPagerIndicator
import dev.soul.shared.theme.CustomThemeManager
import org.jetbrains.compose.resources.painterResource

@Composable
fun PagingImage(
    modifier: Modifier = Modifier,
    imageList: List<StadiumImageModel>,
    onLiked: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { imageList.size })

    Box(
        modifier = modifier.fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp)
        ) { page ->
            AsyncImage(
                model = imageList[page].image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(Resources.Image.Stadium)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        CustomPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            pagerState = pagerState,
            pageCount = imageList.size,
            activeColor = Color.Green,
            inactiveColor = Color.LightGray,
        )

        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(38.dp)
                .background(CustomThemeManager.colors.blurGreen, shape = CircleShape)
                .padding(4.dp)
                .align(Alignment.TopEnd),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Resources.Icon.EmptyHeart),
                contentDescription = null,
                tint = CustomThemeManager.colors.mainColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}