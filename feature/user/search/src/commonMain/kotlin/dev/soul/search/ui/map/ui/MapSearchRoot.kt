package dev.soul.search.ui.map.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.soul.search.components.MapComponent
import dev.soul.search.ui.map.MapSearchEvent
import dev.soul.search.ui.map.MapSearchState
import dev.soul.search.ui.map.MapSearchViewModel
import dev.soul.search.ui.map.components.FilterSection
import dev.soul.shared.Resources
import dev.soul.shared.components.BaseBox
import dev.soul.shared.components.BaseToolbar
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun MapSearchRoot(
    modifier: Modifier = Modifier,
    viewModel: MapSearchViewModel,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val layoutDirection = LocalLayoutDirection.current

    Scaffold(
        containerColor = CustomThemeManager.colors.baseScreenBackground,
        topBar = {
            Column {
                BaseToolbar(
                    name = "Поиск по карте",
                    onBack = {
                        viewModel.onEvent(MapSearchEvent.Back)
                    },
                    menu1 = Resources.Icon.Save,
                    menu2 = Resources.Icon.Search,
                )

                FilterSection(
                    filters = listOf("Type", "Price", "Rate"),
                    selectedValues = mapOf(
                        "Type" to "All",
                        "Price" to "All",
                        "Rate" to "All"
                    ),
                    onFilterSelected = { filter, value ->
                        // Handle filter selection
                    }
                )
            }
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
                onEvent = viewModel::onEvent,
            )
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    state: MapSearchState,
    onEvent: (MapSearchEvent) -> Unit
) {
    BaseBox(modifier) {
        MapComponent(state.stadiums)
    }
}