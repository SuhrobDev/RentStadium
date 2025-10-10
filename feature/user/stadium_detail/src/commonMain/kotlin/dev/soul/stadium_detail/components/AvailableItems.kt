package dev.soul.stadium_detail.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.soul.domain.model.user.available.response.AvailableModel
import dev.soul.shared.FontSize
import dev.soul.shared.boldFont
import dev.soul.shared.components.TextView
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.shared.utils.formatTime

@Composable
fun AvailableItems(
    modifier: Modifier = Modifier,
    item: AvailableModel,
    isSelected: Boolean = false,
    onItemClick: (AvailableModel) -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (item.isBooked) CustomThemeManager.colors.mainColor
            else if (isSelected) CustomThemeManager.colors.mainColor
            else if (item.isActive) CustomThemeManager.colors.baseScreenBackground
            else Color.Transparent,
            contentColor = if (item.isActive) CustomThemeManager.colors.textColor else CustomThemeManager.colors.textColor.copy(
                alpha = 0.5f
            )
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        border = if (isSelected) {
            null
        } else if (item.isActive) {
            null
        } else {
            CardDefaults.outlinedCardBorder(false)
                .copy(width = 1.dp, brush = Brush.linearGradient(listOf(Color.Gray, Color.Gray)))
        },
        interactionSource = remember { MutableInteractionSource() },
        onClick = {
            if (item.isActive) {
                if (item.isBooked.not())
                    onItemClick(item)
            }
        }
    ) {
        Column(
            Modifier.fillMaxWidth().wrapContentWidth().padding(vertical = 8.dp, horizontal = 6.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
        ) {
            TextView(
                text = "${formatTime(item.startTime)}-${formatTime(item.endTime)}",
                modifier = Modifier,
                textColor = if (item.isBooked) Color.White else CustomThemeManager.colors.textColor,
                fontFamily = boldFont(),
                fontSize = FontSize.REGULAR
            )
//            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
//                Icon(
//                    painter = painterResource(Resources.Icon.Person),
//                    null,
//                    tint = CustomThemeManager.colors.textColor.copy(alpha = 0.7f),
//                    modifier = Modifier.size(12.dp)
//                )
//            }
        }
    }
}