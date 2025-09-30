package dev.soul.stadium_detail.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.soul.shared.components.BaseBox
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.stadium_detail.StadiumDetailEvent
import dev.soul.stadium_detail.StadiumDetailState
import dev.soul.stadium_detail.StadiumDetailViewModel
import dev.soul.stadium_detail.components.PagingImage

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun StadiumDetailRoot(
    modifier: Modifier = Modifier,
    viewModel: StadiumDetailViewModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val layoutDirection = LocalLayoutDirection.current
    val lazyListState = rememberLazyListState()

    Scaffold(
        containerColor = CustomThemeManager.colors.baseScreenBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    PaddingValues(
                        start = innerPadding.calculateStartPadding(layoutDirection),
                        end = innerPadding.calculateEndPadding(layoutDirection)
                    )
                )
        ) {
            Content(
                lazyListState = lazyListState,
                state = state,
                onEvent = viewModel::onEvent,
            )
        }
    }
}

@Composable
internal fun Content(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    state: StadiumDetailState,
    onEvent: (StadiumDetailEvent) -> Unit,
) {
    BaseBox(
        modifier = Modifier,
        isLoading = state.isLoading
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize(),
            ) {
                state.stadiumDetail?.images?.let {
                    item {
                        PagingImage(modifier = Modifier.fillMaxWidth().height(242.dp), imageList = it, onSaved = {})
                    }
                }
            }
        }
    }
}