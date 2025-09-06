package dev.soul.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.soul.shared.Resources
import dev.soul.shared.components.SearchView
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CollapsingTopBar(
    modifier: Modifier = Modifier,
    isFocused: Boolean,
    searchQuery: String,
    onFocusedChange: (Boolean) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onNotificationClick: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onNotificationClick) {
                Icon(
                    painter = painterResource(Resources.Icon.EmptyHeart),
                    contentDescription = "Notifications",
                    tint = Color(0xFF969FA8)
                )
            }
        }

        Text(
            text = "Search",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
        )

        SearchView(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            onFocused = { focused -> onFocusedChange(focused) },
            isFocused = isFocused,
            modifier = Modifier.fillMaxWidth()
        )
    }
}