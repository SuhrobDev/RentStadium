package dev.soul.stadium_detail.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.soul.shared.components.BaseBox
import dev.soul.shared.components.ButtonView
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.stadium_detail.StadiumDetailEvent
import dev.soul.stadium_detail.StadiumDetailState
import dev.soul.stadium_detail.StadiumDetailViewModel
import dev.soul.stadium_detail.components.AboutSection
import dev.soul.stadium_detail.components.ActionSection
import dev.soul.stadium_detail.components.AppBar
import dev.soul.stadium_detail.components.NameSection
import dev.soul.stadium_detail.components.PagingImage
import dev.soul.stadium_detail.components.PriceSection
import dev.soul.stadium_detail.components.StadiumLocationSection

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
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )
            AppBar(
                modifier = Modifier.align(Alignment.TopCenter),
                onNavigationClick = onBack,
                onShare = {
                    state.stadiumDetail?.id?.let {
                        viewModel.onEvent(StadiumDetailEvent.Share(it))
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun Content(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    state: StadiumDetailState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onEvent: (StadiumDetailEvent) -> Unit,
) {
    BaseBox(
        modifier = Modifier,
        isLoading = state.isLoading
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.weight(1f),
            ) {
                state.stadiumDetail?.images?.let {
                    item {
                        PagingImage(
                            modifier = Modifier.fillMaxWidth().height(300.dp),
                            imageList = it,
                            sharedTransitionScope = sharedTransitionScope,
                            animatedContentScope = animatedContentScope
                        )
                    }
                }
                item {
                    Spacer(Modifier.height(16.dp))
                    NameSection(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        name = state.stadiumDetail?.name ?: "",
                        rating = state.stadiumDetail?.rating ?: "",
                        address = state.stadiumDetail?.address ?: ""
                    )
                    Spacer(Modifier.height(24.dp))
                }

                item {
                    ActionSection(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        onMapClick = {

                        },
                        onFriendClick = {

                        },
                        onSaveClick = {

                        }
                    )
                    Spacer(Modifier.height(24.dp))
                }

                item {
                    PriceSection(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        price = state.stadiumDetail?.price ?: ""
                    )
                    Spacer(Modifier.height(24.dp))
                }

                item {
                    AboutSection(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        about = "Tashkent Stadium is one of the largest and most modern sports complexes in Central Asia. Built in 2022, it offers a seating capacity of more than 35,000 spectators and is equipped with high-quality LED lighting, a retractable roof, and advanced turf maintenance systems. The stadium regularly hosts football matches, concerts, and national ceremonies, serving as a central hub for cultural and sporting events. Its surrounding area includes parking, training fields, restaurants, and a fitness center. The design of the stadium combines modern architecture with traditional Uzbek elements, making it not only a sports venue but also a symbol of pride for the city. Visitors often praise the accessibility, cleanliness, and overall atmosphere during events."
                    )
                }

                item {
                    Spacer(Modifier.height(16.dp))

                    state.stadiumDetail?.location?.let {
                        StadiumLocationSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            locationModel = it,
                            onNavigate = {

                            }
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }

            Box(Modifier.fillMaxWidth().height(96.dp).background(CustomThemeManager.colors.screenBackground)){
                ButtonView(
                    modifier = Modifier.align(Alignment.TopCenter).padding(horizontal = 16.dp, vertical = 16.dp),
                    text = "Забронировать",
                    textColor = Color.White,
                    onClick = {},
                )
            }

        }
    }
}