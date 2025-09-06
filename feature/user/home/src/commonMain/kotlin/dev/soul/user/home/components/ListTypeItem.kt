package dev.soul.user.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.soul.shared.FontSize
import dev.soul.shared.Resources
import dev.soul.shared.components.TextView
import dev.soul.shared.theme.CustomThemeManager
import org.jetbrains.compose.resources.painterResource

@Composable
fun ListTypeItem(modifier: Modifier = Modifier, title: String, onSeeAll: () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextView(
            text = title,
            fontSize = FontSize.EXTRA_REGULAR,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onSeeAll()
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            TextView(
                text = "Смотреть все",
                fontWeight = FontWeight.Medium,
                textColor = CustomThemeManager.colors.mainColor
            )

            Icon(
                painter = painterResource(Resources.Icon.RightArrow),
                contentDescription = null,
                tint = CustomThemeManager.colors.mainColor,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}