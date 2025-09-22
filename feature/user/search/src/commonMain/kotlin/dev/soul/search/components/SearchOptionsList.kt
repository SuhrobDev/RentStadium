package dev.soul.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.soul.domain.model.user.search.StadiumOptions
import dev.soul.shared.Resources
import dev.soul.shared.navigation.Screen
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun SearchOptionsList(
    options: List<StadiumOptions>,
    modifier: Modifier = Modifier,
    onOptionClick: (Screen) -> Unit
) {
    FlowRow(
        modifier = modifier.padding(12.dp),
        maxItemsInEachRow = 3,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        options.forEach { stadium ->
            SearchOption(
                modifier = Modifier
                    .weight(1f, fill = true) // try to spread evenly
                    .fillMaxWidth(),
                onClick = onOptionClick,
                icon = Resources.Icon.Map,
                text = stadium.title,
                screen = stadium.screen
            )
        }
    }
}