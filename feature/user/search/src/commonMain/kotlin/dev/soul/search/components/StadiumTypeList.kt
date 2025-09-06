package dev.soul.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.soul.shared.FontSize
import dev.soul.shared.components.TextView
import dev.soul.shared.semiBoldFont
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun StadiumTypeList(
    options: List<Triple<DrawableResource, String, String>>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(12.dp)
    ) {

        TextView(
            text = "Типы стадионов",
            fontFamily = semiBoldFont(),
            fontSize = FontSize.MEDIUM
        )

        Spacer(Modifier.height(16.dp))

        FlowRow(
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            options.forEach { (icon, text, distance) ->
                StadiumType(
                    modifier = Modifier
                        .weight(1f, fill = true) // try to spread evenly
                        .fillMaxWidth(),
                    icon = icon,
                    text = text,
                    distance = distance
                )
            }
        }
    }
}