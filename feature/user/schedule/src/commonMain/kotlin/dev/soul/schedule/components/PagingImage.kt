package dev.soul.schedule.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.soul.domain.model.user.schedule.response.ScheduleStatus
import dev.soul.domain.model.user.search.response.StadiumImageModel
import dev.soul.shared.Resources
import dev.soul.shared.theme.CustomThemeManager
import org.jetbrains.compose.resources.painterResource

@Composable
fun PagingImage(
    modifier: Modifier = Modifier,
    imageList: List<StadiumImageModel>,
    status: ScheduleStatus,
    onFriendClick: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { imageList.size })

    Box(
        modifier = modifier.fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
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
                contentScale = ContentScale.Crop,
                placeholder = painterResource(Resources.Image.Stadium)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            Modifier.align(Alignment.BottomStart).padding(bottom = 8.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatusBadge(
                status = status,
            )

            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = CustomThemeManager.colors.lightGray,
                    contentColor = CustomThemeManager.colors.mainColor
                ),
                content = {
                    Icon(
                        painter = painterResource(Resources.Icon.AddFriend),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                onClick = {
                    onFriendClick()
                })
        }
    }
}