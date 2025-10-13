package dev.soul.schedule.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.soul.schedule.ScheduleEvent
import dev.soul.schedule.ScheduleState
import dev.soul.schedule.ScheduleViewModel
import dev.soul.schedule.components.ScheduleItem
import dev.soul.shared.Resources
import dev.soul.shared.components.CustomToast
import dev.soul.shared.components.EmptyLiked
import dev.soul.shared.components.TextView
import dev.soul.shared.components.ToastStatus
import dev.soul.shared.navigation.Screen
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.shared.utils.UiEvent
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleRoot(
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel,
    onHistory: () -> Unit,
    onDetail: (Int) -> Unit,
    onNotification: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val layoutDirection = LocalLayoutDirection.current
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    // Smooth text size interpolation based on collapse fraction
    val titleFontSize = lerp(
        start = 30.sp, // FontSize.LARGE
        stop = 20.sp,  // Collapsed size
        fraction = scrollBehavior.state.collapsedFraction
    )

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onDetail((event.route as Screen.StadiumDetail).scheduleDetail!!)
                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message.asString())
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = CustomThemeManager.colors.baseScreenBackground,
        topBar = {
            LargeTopAppBar(
                title = {
                    TextView(
                        text = "Schedule",
                        textColor = CustomThemeManager.colors.textColor,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontSize = titleFontSize,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(onClick = onHistory) {
                            Icon(
                                painter = painterResource(Resources.Icon.HistorySchedule),
                                contentDescription = "History",
                                tint = CustomThemeManager.colors.textColor
                            )
                        }
                        IconButton(onClick = onNotification) {
                            Icon(
                                painter = painterResource(Resources.Icon.Notification),
                                contentDescription = "Notification",
                                tint = CustomThemeManager.colors.textColor
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = CustomThemeManager.colors.baseScreenBackground,
                    scrolledContainerColor = CustomThemeManager.colors.baseScreenBackground
                ),
                scrollBehavior = scrollBehavior
            )
        }
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
    state: ScheduleState,
    onEvent: (ScheduleEvent) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        onEvent(ScheduleEvent.Refresh)
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        state = listState
    ) {
        if (state.schedules.isEmpty())
            item {
                EmptyLiked()
            }

        items(state.schedules, key = { "${it.id}_${it.stadium?.id}_$it" }) {
            ScheduleItem(
                modifier = Modifier.fillMaxWidth(),
                item = it,
                onItemClick = {
                    onEvent(ScheduleEvent.Detail(it.id))
                },
                onFriendClick = {

                },
                onRouteClick = {

                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(96.dp))
        }
    }
}