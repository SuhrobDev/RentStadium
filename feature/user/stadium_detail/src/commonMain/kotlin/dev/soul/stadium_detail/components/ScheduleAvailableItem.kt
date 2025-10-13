package dev.soul.stadium_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.soul.domain.model.user.schedule.detail.response.ScheduleDetailModel
import dev.soul.shared.FontSize
import dev.soul.shared.Resources
import dev.soul.shared.boldFont
import dev.soul.shared.components.TextView
import dev.soul.shared.utils.formatDateAndTime
import dev.soul.shared.utils.getDayOfWeek
import dev.soul.stadium_detail.ui.toUI
import org.jetbrains.compose.resources.painterResource

@Composable
fun ScheduleAvailableItem(
    modifier: Modifier = Modifier,
    item: ScheduleDetailModel,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = item.status.toUI().backgroundColor.copy(
                alpha = 0.1f
            )
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        border = null
    ) {
        Row(
            Modifier.fillMaxWidth().heightIn(min = 62.dp).padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextView(
                        text = formatDateAndTime(item.datetimeRange?.lower ?: "").first,
                        fontFamily = boldFont(),
                        fontSize = FontSize.REGULAR,
                        textColor = item.status.toUI().backgroundColor
                    )
                    TextView(
                        text = getDayOfWeek(item.datetimeRange?.lower ?: ""),
                        textColor = item.status.toUI().backgroundColor
                    )
                }

                Box(
                    modifier = Modifier.width(1.dp).heightIn(min = 48.dp)
                        .padding(vertical = 8.dp)
                        .background(item.status.toUI().backgroundColor)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        8.dp,
                        Alignment.CenterVertically
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        TextView(
                            text = formatDateAndTime(item.datetimeRange?.lower ?: "").second,
                            fontFamily = boldFont(),
                            fontSize = FontSize.REGULAR,
                            textColor = item.status.toUI().backgroundColor
                        )

                        TextView(
                            text = "60 mins",
                            textColor = item.status.toUI().backgroundColor,
                            fontSize = FontSize.EXTRA_SMALL
                        )
                    }

                    TextView(text = item.status.toUI().text, textColor = item.status.toUI().backgroundColor)

//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(4.dp)
//                    ) {
//                        Icon(
//                            painter = painterResource(Resources.Icon.Person),
//                            null,
//                            modifier = Modifier.size(12.dp),
//                            tint = Color.Gray
//                        )
//
//                        TextView(text = "${item.dayOfWeek}", textColor = Color.Gray)
//                    }
                }
            }

            Icon(
                painter = painterResource(Resources.Icon.RightArrow),
                contentDescription = null,
                tint = item.status.toUI().backgroundColor
            )
        }
    }
}