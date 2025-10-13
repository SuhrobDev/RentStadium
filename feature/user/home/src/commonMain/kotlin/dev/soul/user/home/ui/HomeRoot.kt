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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.soul.shared.Resources
import dev.soul.shared.components.BaseBox
import dev.soul.shared.components.CustomToast
import dev.soul.shared.components.SearchView
import dev.soul.shared.components.ToastStatus
import dev.soul.shared.navigation.Screen
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.shared.utils.Logger
import dev.soul.shared.utils.UiEvent
import dev.soul.user.home.HomeEvent
import dev.soul.user.home.HomeState
import dev.soul.user.home.HomeViewModel
import dev.soul.user.home.components.CatalogItem
import dev.soul.user.home.components.HomeStadionItem
import dev.soul.user.home.components.ListTypeItem
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeRoot(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onDetail: (Int) -> Unit,
    onMore: (Boolean) -> Unit,
    onLiked: () -> Unit
) {
    val layoutDirection = LocalLayoutDirection.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    when (event.route) {
                        is Screen.StadiumDetail -> {
                            onDetail((event.route as Screen.StadiumDetail).detail!!)
                        }

                        is Screen.More -> {
                            if ((event.route as Screen.More).isPopular == true)
                                onMore(true)
                            else
                                onMore(false)
                        }

                        is Screen.Liked -> {
                            onLiked()
                        }

                        else -> {}
                    }
                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message.asString())
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        containerColor = CustomThemeManager.colors.baseScreenBackground
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    PaddingValues(
                        top = innerPadding.calculateTopPadding(),
                        start = innerPadding.calculateStartPadding(layoutDirection),
                        end = innerPadding.calculateEndPadding(layoutDirection)
                    )
                ),
        ) {
            Content(
                modifier = Modifier.fillMaxSize(),
                state = state,
                onEvent = viewModel::onEvent
            )

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 12.dp),
                snackbar = { data ->
                    CustomToast(
                        message = data.visuals.message,
                        onDismiss = {
                            snackbarHostState
                                .currentSnackbarData?.dismiss()
                        },
                        status = if (state.success) ToastStatus.SUCCESS else ToastStatus.ERROR
                    )
                }
            )
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(state.popularList) {
        Logger.log("fdsqweeqtw", "${state.popularList}")
    }

    BaseBox {
        Column(
            modifier = modifier,
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
                        onEvent(HomeEvent.Liked)
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
                    if (state.popularLoading)
                        CircularProgressIndicator()
                    else
                        ListTypeItem(
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .padding(horizontal = 16.dp),
                            title = "Popular",
                            onSeeAll = {
                                onEvent(HomeEvent.MorePopular)
                            }
                        )
                }

                item {
                    if (state.popularLoading)
                        CircularProgressIndicator()
                    else
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(
                                items = state.popularList,
                                key = { "${it.id}_${it.location.coordinates.first()}_${it.location.coordinates.last()}" }) {
                                HomeStadionItem(
                                    modifier = Modifier.width(168.dp), it, onClick = {
                                        onEvent(HomeEvent.Detail(it))
                                    },
                                    onLiked = { id, current ->
                                        onEvent(HomeEvent.Like(id, it.liked, true))
                                    })
                            }

                        }
                }

                item {
                    if (state.personalizedLoading)
                        CircularProgressIndicator()
                    else if (state.personalizedList.isNotEmpty())
                        ListTypeItem(
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .padding(horizontal = 16.dp),
                            title = "Personalized",
                            onSeeAll = {
                                onEvent(HomeEvent.MorePersonalized)
                            }
                        )
                }

                item {
                    if (state.personalizedLoading)
                        CircularProgressIndicator()
                    else
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(
                                items = state.personalizedList,
                                key = { "${it.id}_${it.location.coordinates.first()}_${it.location.coordinates.last()}" }) {
                                HomeStadionItem(
                                    modifier = Modifier.width(168.dp), it, onClick = {
                                        onEvent(HomeEvent.Detail(it))
                                    },
                                    onLiked = { id, current ->
                                        onEvent(HomeEvent.Like(id, it.liked, false))
                                    })
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