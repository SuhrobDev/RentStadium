package dev.soul.stadium_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.soul.domain.model.user.available.response.AvailableModel
import dev.soul.shared.FontSize
import dev.soul.shared.Resources
import dev.soul.shared.boldFont
import dev.soul.shared.components.TextView
import dev.soul.shared.theme.CustomThemeManager
import org.jetbrains.compose.resources.painterResource

@Composable
fun AvailableItems(
    modifier: Modifier = Modifier,
    item: AvailableModel,
    onItemClick: (AvailableModel) -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (item.isBooked) CustomThemeManager.colors.mainColor else
                if (item.isActive) CustomThemeManager.colors.baseScreenBackground else Color.Transparent,
            contentColor = if (item.isActive) CustomThemeManager.colors.textColor else CustomThemeManager.colors.textColor.copy(
                alpha = 0.5f
            )
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        border = CardDefaults.outlinedCardBorder(item.isActive)
            .copy(width = 1.dp, brush = Brush.linearGradient(listOf(Color.Gray))),
        onClick = {
            onItemClick(item)
        }
    ) {
        Column(
            Modifier.fillMaxWidth().wrapContentWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextView(
                text = "${item.startTime}-${item.endTime}",
                modifier = Modifier,
                textColor = CustomThemeManager.colors.textColor,
                fontFamily = boldFont(),
                fontSize = FontSize.REGULAR
            )
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(
                    painter = painterResource(Resources.Icon.Person),
                    null,
                    tint = CustomThemeManager.colors.textColor.copy(alpha = 0.7f),
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}