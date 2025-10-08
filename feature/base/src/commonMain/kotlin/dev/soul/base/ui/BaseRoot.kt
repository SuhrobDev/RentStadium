package dev.soul.base.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.soul.base.components.BottomBar
import dev.soul.search.SearchViewModel
import dev.soul.search.ui.SearchRoot
import dev.soul.shared.navigation.Screen
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.shared.utils.TabScreens
import dev.soul.user.home.HomeViewModel
import dev.soul.user.home.ui.HomeRoot

@Composable
fun BaseGraphRoot(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    onNotification: () -> Unit,
    onSearchOption: (Screen) -> Unit,
    onDetail: (Int) -> Unit,
    onMore: (Boolean) -> Unit,
) {

    val navController = rememberNavController()
    val currentRoute by rememberUpdatedState(newValue = navController.currentBackStackEntryAsState().value?.destination?.route)
    var navigatedScreen by remember {
        mutableStateOf("")
    }

    LaunchedEffect(currentRoute) {
        currentRoute?.let {
            navigatedScreen = it.split(".").last()
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NavHost(
            modifier = Modifier.weight(1f),
            navController = navController,
            startDestination = TabScreens.Home
        ) {
            composable<TabScreens.Home> {
                HomeRoot(
                    viewModel = homeViewModel,
                    onDetail = onDetail,
                    onMore = onMore
                )
            }

            composable<TabScreens.Schedule> {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(CustomThemeManager.colors.mainColor)
                )
            }

            composable<TabScreens.Search> {
                SearchRoot(
                    viewModel = searchViewModel,
                    onNotification = onNotification,
                    onSearchOption = onSearchOption
                )
            }

            composable<TabScreens.Shop> {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(CustomThemeManager.colors.blurRedColor)
                )

            }

            composable<TabScreens.Profile> {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(CustomThemeManager.colors.borderColor)
                )
            }
        }
        Box(
            modifier = Modifier
                .background(CustomThemeManager.colors.screenBackground)
                .padding(all = 12.dp)
        ) {

            BottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                currentRoute = navigatedScreen,
                onTabSelected = { tab ->
                    if (currentRoute != tab.route) {
                        navController.navigate(tab) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }

}

@Composable
internal fun Content() {

}