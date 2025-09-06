package dev.soul.auth.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.soul.auth.component.EntryImagePlaceHolder
import dev.soul.auth.component.EntryInfo
import dev.soul.shared.Resources
import dev.soul.shared.components.BaseBox
import dev.soul.shared.components.ButtonView
import dev.soul.shared.components.CustomPagerIndicator
import dev.soul.shared.theme.CustomThemeManager
import kotlinx.coroutines.launch

@Composable
fun EntryRoot(
    modifier: Modifier = Modifier,
    onNavigateLogin: () -> Unit
) {
    Content()
}

@Composable
private fun Content() {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val scope = rememberCoroutineScope()
    BaseBox {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
            ) { page ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(50.dp)
                ) {
                    EntryImagePlaceHolder(img = Resources.Icon.Entry1)

                    EntryInfo(
                        title = "Stadionni bron qilish hech qachon bu darajada oson bo‘lmagan",
                        desc = "Har qanday vaqtda yaqin atrofdagi stadionni toping va bir necha bosishda bron qiling. Endi o‘yinlar rejasiz o‘tmaydi!"
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            ButtonView(
                onClick = {
                    if (pagerState.canScrollForward)
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                },
                containerColor = CustomThemeManager.colors.lightGray,
                text = "Ajoyib",
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomPagerIndicator(
                pagerState = pagerState,
                pageCount = 3,
                activeColor = Color.Black,
                inactiveColor = Color.LightGray,
                indicatorWidth = 20.dp,
                indicatorHeight = 6.dp,
                spacing = 6.dp,
                shape = RoundedCornerShape(3.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

        }
    }
}