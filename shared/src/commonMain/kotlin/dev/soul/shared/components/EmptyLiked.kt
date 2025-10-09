package dev.soul.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.soul.shared.FontSize
import dev.soul.shared.Resources
import org.jetbrains.compose.resources.painterResource

@Composable
fun EmptyLiked(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(Resources.Image.EmptySchedule),
                contentDescription = null,
                modifier = Modifier.size(240.dp)
            )

            TextView(
                text = "No liked yet",
                fontSize = FontSize.REGULAR,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}