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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.soul.shared.components.BaseBox
import dev.soul.shared.components.ButtonView
import dev.soul.shared.components.CustomToast
import dev.soul.shared.components.ToastStatus
import dev.soul.shared.navigation.Screen
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.shared.utils.UiEvent
import dev.soul.stadium_detail.StadiumDetailEvent
import dev.soul.stadium_detail.StadiumDetailState
import dev.soul.stadium_detail.StadiumDetailViewModel
import dev.soul.stadium_detail.components.AboutSection
import dev.soul.stadium_detail.components.ActionSection
import dev.soul.stadium_detail.components.AppBar
import dev.soul.stadium_detail.components.ConfirmBottomSheet
import dev.soul.stadium_detail.components.NameSection
import dev.soul.stadium_detail.components.PagingImage
import dev.soul.stadium_detail.components.PriceSection
import dev.soul.stadium_detail.components.ScheduleBottomSheet
import dev.soul.stadium_detail.components.SelectedAvailableItem
import dev.soul.stadium_detail.components.StadiumLocationSection
import kotlinx.coroutines.launch

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
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message.asString())
                }

                is UiEvent.NavigateUp -> {
                    onBack()
                }
            }
        }
    }

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

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun Content(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    state: StadiumDetailState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onEvent: (StadiumDetailEvent) -> Unit,
) {
    val scheduleSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val confirmSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

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

            Column(
                Modifier.fillMaxWidth().heightIn(min = 96.dp)
                    .background(CustomThemeManager.colors.screenBackground),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                if (state.selectedAvailable.isNotEmpty())
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
                    ) {
                        items(state.selectedAvailable, key = { "${it.id}_${it.stadium}_$it" }) {
                            SelectedAvailableItem(
                                modifier = Modifier.fillMaxWidth(),
                                item = it,
                                weeks = state.upcomingDays,
                                onItemClick = {
                                    onEvent(StadiumDetailEvent.SelectedWeekTab(it.dayOfWeekDisplay))
                                    scope.launch {
                                        scheduleSheetState.show()
                                    }
                                }
                            )
                        }
                    }

                ButtonView(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    text =
                        if (state.selectedAvailable.isEmpty())
                        "Расписание"
                    else
                        "Забронировать",
                    textColor = Color.White,
                    isLoading = state.dateLoading,
                    enabled = state.upcomingDays.isNotEmpty(),
                    onClick = {
                        if (state.selectedAvailable.isEmpty())
                            scope.launch {
                                scheduleSheetState.show()
                            }
                        else
                            scope.launch {
                                confirmSheetState.show()
                            }
                    }
                )
                Spacer(Modifier.height(32.dp))
            }
        }

        if (scheduleSheetState.isVisible) {
            LaunchedEffect(scheduleSheetState.isVisible) {
                if (scheduleSheetState.isVisible && state.selectedDate != null) {
                    onEvent(StadiumDetailEvent.DateSelect(state.selectedDate))
                }
            }

            ScheduleBottomSheet(
                sheetState = scheduleSheetState,
                weeks = state.upcomingDays,
                onWeekClick = {
                    onEvent(StadiumDetailEvent.DateSelect(it))
                },
                selectedDate = state.selectedDate ?: 0,
                available = state.available,
                selectedAvailable = state.selectedAvailable,
                availableLoading = state.availableLoading,
                onAvailableClick = {
                    onEvent(StadiumDetailEvent.AvailableSelect(it))
                },
                onDismiss = {
                    scope.launch {
                        onEvent(StadiumDetailEvent.DateSelect(0))
                        scheduleSheetState.hide()
                    }
                }
            )
        }

        if (confirmSheetState.isVisible) {
            ConfirmBottomSheet(
                sheetState = confirmSheetState,
                stadiumDetail = state.stadiumDetail,
                loading = state.bookLoading,
                selectedAvailable = state.selectedAvailable,
                onConfirm = {
                    onEvent(StadiumDetailEvent.Book)
                },
                onPrivacyClick = {

                },
                onDismiss = {
                    scope.launch {
                        confirmSheetState.hide()
                    }
                }
            )
        }

    }
}