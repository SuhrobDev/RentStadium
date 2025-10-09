package dev.soul.liked.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.soul.liked.LikedEvent
import dev.soul.liked.LikedState
import dev.soul.liked.LikedViewModel
import dev.soul.liked.components.StadiumItem
import dev.soul.shared.components.BaseBox
import dev.soul.shared.components.BaseToolbar
import dev.soul.shared.components.CustomToast
import dev.soul.shared.components.EmptyLiked
import dev.soul.shared.components.ToastStatus
import dev.soul.shared.navigation.Screen
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.shared.utils.UiEvent

@Composable
fun LikedRoot(
    modifier: Modifier = Modifier,
    viewModel: LikedViewModel,
    onDetail: (Int) -> Unit,
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val layoutDirection = LocalLayoutDirection.current

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onDetail((event.route as Screen.StadiumDetail).id)
                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message.asString())
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = modifier,
        containerColor = CustomThemeManager.colors.baseScreenBackground,
        topBar = {
            BaseToolbar(
                name = "Liked",
                onBack = {
                    onBack()
                }
            )
        }
    ) { innerPadding ->
        BaseBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    PaddingValues(
                        top = innerPadding.calculateTopPadding(),
                        start = innerPadding.calculateStartPadding(layoutDirection),
                        end = innerPadding.calculateEndPadding(layoutDirection)
                    )
                ),
            isLoading = state.isInitialLoading
        ) {

            Content(
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
internal fun Content(
    modifier: Modifier = Modifier,
    state: LikedState,
    onEvent: (LikedEvent) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        state = listState
    ) {

        itemsIndexed(
            state.likedList,
            key = { index, _ -> index }
        ) { index, item ->
            StadiumItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                stadium = item,
                onStadiumClick = {
                    onEvent(LikedEvent.Detail(it))
                },
                onLiked = { id, current ->
                    onEvent(LikedEvent.Liked(id, current))
                }
            )
        }

        if (state.likedList.isEmpty())
            item {
                EmptyLiked()
            }

        item {
            if (state.hasNextPage && !state.isLoadingMore) {
                SideEffect {
                    onEvent(LikedEvent.More)
                }
            }
        }

        if (state.isLoadingMore) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(16.dp),
                        strokeWidth = 2.dp,
                        trackColor = CustomThemeManager.colors.mainColor,
                        color = CustomThemeManager.colors.lightGray
                    )
                }
                Spacer(modifier = Modifier.height(96.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(96.dp))
        }
    }
}