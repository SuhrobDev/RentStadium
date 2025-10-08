package dev.soul.stadium_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.soul.shared.FontSize
import dev.soul.shared.Resources
import dev.soul.shared.components.TextView
import dev.soul.shared.semiBoldFont
import dev.soul.shared.theme.CustomThemeManager
import org.jetbrains.compose.resources.painterResource

@Composable
fun NameSection(
    modifier: Modifier = Modifier,
    name: String,
    rating: String,
    address: String
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        TextView(
            text = name,
            fontSize = FontSize.EXTRA_MEDIUM,
            fontFamily = semiBoldFont(),
            maxLines = 2
        )

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            TextView(text = rating, fontSize = FontSize.SMALL)
            Icon(
                modifier = Modifier.padding(horizontal = 2.dp),
                painter = painterResource(Resources.Icon.Star),
                contentDescription = null,
                tint = CustomThemeManager.colors.yellowColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextView(
                text = address,
                fontSize = FontSize.SMALL,
                textColor = CustomThemeManager.colors.textColor.copy(0.6f)
            )
        }
    }
}