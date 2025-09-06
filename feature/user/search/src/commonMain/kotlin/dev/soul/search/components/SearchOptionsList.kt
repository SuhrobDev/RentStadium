package dev.soul.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun SearchOptionsList(
    options: List<Pair<DrawableResource, String>>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier.padding(12.dp),
        maxItemsInEachRow = 3,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        options.forEach { (icon, text) ->
            SearchOption(
                modifier = Modifier
                    .weight(1f, fill = true) // try to spread evenly
                    .fillMaxWidth(),
                icon = icon,
                text = text
            )
        }
    }
}