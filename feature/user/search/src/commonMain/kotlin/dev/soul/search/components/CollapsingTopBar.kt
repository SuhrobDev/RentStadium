package dev.soul.search.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import dev.soul.shared.Resources
import dev.soul.shared.components.SearchView
import dev.soul.shared.components.TextView
import dev.soul.shared.theme.CustomThemeManager
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingTopBar(
    modifier: Modifier = Modifier,
    isFocused: Boolean,
    searchQuery: String,
    onFocusedChange: (Boolean) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onNotificationClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    // Smooth interpolations based on collapse fraction
    val collapseFraction = scrollBehavior.state.collapsedFraction

    // Title size interpolation (30sp to 20sp)
    val titleFontSize = lerp(
        start = 30.sp,
        stop = 20.sp,
        fraction = collapseFraction
    )

    // Title alpha (fade out when collapsing)
    val titleAlpha by animateFloatAsState(
        targetValue = 1f - collapseFraction,
        label = "titleAlpha"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        LargeTopAppBar(
            title = {
                // Title - fades out when collapsing
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(titleAlpha)
                        .graphicsLayer {
                            // Slightly scale down when collapsing for smooth effect
                            val scale = 1f - (collapseFraction * 0.1f)
                            scaleX = scale
                            scaleY = scale
                        }
                ) {
                    TextView(
                        text = "Search",
                        textColor = CustomThemeManager.colors.textColor,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontSize = titleFontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            actions = {
                // Notification button - always visible
                IconButton(onClick = onNotificationClick) {
                    Icon(
                        painter = painterResource(Resources.Icon.Notification),
                        contentDescription = "Notifications",
                        tint = CustomThemeManager.colors.textColor
                    )
                }
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = CustomThemeManager.colors.baseScreenBackground,
                scrolledContainerColor = CustomThemeManager.colors.baseScreenBackground
            ),
            scrollBehavior = scrollBehavior
        )

        // Search bar - positioned below the top bar, always visible
        SearchView(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            onFocused = { focused -> onFocusedChange(focused) },
            isFocused = isFocused,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        )
    }
}
