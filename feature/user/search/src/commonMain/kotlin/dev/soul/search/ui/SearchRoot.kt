package dev.soul.search.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.soul.search.SearchEvent
import dev.soul.search.SearchState
import dev.soul.search.SearchViewModel
import dev.soul.search.components.CollapsingTopBar
import dev.soul.search.components.SearchOptionsList
import dev.soul.search.components.SearchResultItem
import dev.soul.search.components.StadiumTypeList
import dev.soul.shared.components.BaseBox
import dev.soul.shared.navigation.Screen
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.shared.utils.Logger

@Composable
fun SearchRoot(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    onNotification: () -> Unit,
    onSearchOption: (Screen) -> Unit
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
                        top = innerPadding.calculateTopPadding(),
                        start = innerPadding.calculateStartPadding(layoutDirection),
                        end = innerPadding.calculateEndPadding(layoutDirection)
                    )
                )
        ) {
            Content(
                lazyListState = lazyListState,
                state = state,
                onEvent = viewModel::onEvent,
                onNotification = onNotification,
                onOption = onSearchOption
            )
        }
    }
}

@Composable
internal fun Content(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    onNotification: () -> Unit,
    onOption: (Screen) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()

    BaseBox(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = MutableInteractionSource()
        ) {
            onEvent(SearchEvent.IsSearchFocused(false))
            focusManager.clearFocus()
            keyboardController?.hide()
        }) {
        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            CollapsingTopBar(
                searchQuery = state.searchQuery,
                onSearchQueryChange = {
                    onEvent(SearchEvent.SearchQuery(it))
                },
                onNotificationClick = onNotification,
                isFocused = state.isSearchFocused,
                onFocusedChange = {
                    if (!it) {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                    onEvent(SearchEvent.IsSearchFocused(it))
                }
            )

            AnimatedVisibility(
                state.isSearchFocused.not(),
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item {
                        SearchOptionsList(
                            options = state.options,
                            modifier = Modifier.weight(1f),
                            onOptionClick = onOption
                        )
                    }

                    item {
                        StadiumTypeList(
                            options = state.types,
                            modifier = Modifier.weight(1f)
                        )
                    }

                }
            }

            AnimatedVisibility(
                state.isSearchFocused,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(state.searchResults, { it.id }) {
                        SearchResultItem(it, {
                            Logger.log("asdqwgds", "$it")
                        })
                    }
                }
            }

        }
    }
}