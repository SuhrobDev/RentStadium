package dev.soul.base.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.soul.shared.Resources
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.shared.utils.TabScreens
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomBar(
    currentRoute: String,
    onTabSelected: (TabScreens) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        TabScreens.Home,
        TabScreens.Schedule,
        TabScreens.Search,
        TabScreens.Shop,
        TabScreens.Profile
    )

    val icon = listOf(
        Resources.Icon.MenuHome,
        Resources.Icon.MenuSchedule,
        Resources.Icon.Search,
        Resources.Icon.MenuShop,
        Resources.Icon.MenuProfile,
    )

    Box(modifier = modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .shadow(
                    elevation = 8.dp,
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = Color.Black.copy(alpha = 0.2f)
                )
                .background(Color.White)
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items.forEachIndexed { index, tab ->
                    IconButton(
                        onClick = {
                            if (currentRoute != tab.route) {
                                onTabSelected(tab)
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(icon[index]),
                            contentDescription = tab.route,
                            tint = if (currentRoute == tab.route)
                                CustomThemeManager.colors.mainColor else Color.Gray
                        )
                    }
                }
            }
        }
    }
}