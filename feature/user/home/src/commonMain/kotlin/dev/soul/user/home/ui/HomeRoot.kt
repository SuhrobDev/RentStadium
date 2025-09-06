package dev.soul.user.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import dev.soul.shared.Resources
import dev.soul.shared.components.BaseBox
import dev.soul.shared.components.SearchView
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.shared.utils.UiEvent
import dev.soul.user.home.components.CatalogItem
import dev.soul.user.home.components.HomeStadionItem
import dev.soul.user.home.components.ListTypeItem
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeRoot(
    modifier: Modifier = Modifier,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val layoutDirection = LocalLayoutDirection.current

    Scaffold(
        containerColor = CustomThemeManager.colors.baseScreenBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    PaddingValues(
                        top = innerPadding.calculateTopPadding(),
                        start = innerPadding.calculateStartPadding(layoutDirection),
                        end = innerPadding.calculateEndPadding(layoutDirection)
                    )
                ),
        ) {
            Content()
        }
    }
}

@Composable
private fun Content(modifier: Modifier = Modifier) {

    val lazyListState = rememberLazyListState()
    BaseBox {
        Column(
            modifier = modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CustomThemeManager.colors.baseScreenBackground)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchView(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.weight(1f),
                    isFocused = false,
                    onFocused = {}
                )

                Icon(
                    painter = painterResource(Resources.Icon.Likes),
                    contentDescription = null,
                    tint = Color(0xFF969FA8),
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {

                    }
                )
            }

            LazyColumn(
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(7) {
                            CatalogItem()
                        }
                    }
                }

                item {
                    ListTypeItem(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .padding(horizontal = 16.dp),
                        title = "Tоп стадионы",
                        onSeeAll = {}
                    )
                }

                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(7) {
                            HomeStadionItem(modifier = Modifier.width(168.dp))
                        }
                    }
                }

                item {
                    ListTypeItem(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .padding(horizontal = 16.dp),
                        title = "Пустые стадионы",
                        onSeeAll = {}
                    )
                }

                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(7) {
                            HomeStadionItem(modifier = Modifier.width(168.dp))
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(56.dp))
                }

            }
        }
    }
}