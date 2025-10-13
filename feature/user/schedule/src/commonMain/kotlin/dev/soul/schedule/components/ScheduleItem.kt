package dev.soul.schedule.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.soul.domain.model.user.schedule.response.ScheduleItemModel
import dev.soul.shared.FontSize
import dev.soul.shared.Resources
import dev.soul.shared.components.TextView
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.shared.utils.formatDateAndTime
import org.jetbrains.compose.resources.painterResource

@Composable
fun ScheduleItem(
    modifier: Modifier = Modifier,
    item: ScheduleItemModel,
    onItemClick: (ScheduleItemModel) -> Unit,
    onFriendClick: (ScheduleItemModel) -> Unit,
    onRouteClick: (ScheduleItemModel) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = {
            onItemClick(item)
        },
        colors = CardDefaults.cardColors(containerColor = CustomThemeManager.colors.screenBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        item.stadium?.let { stadium ->
            Column(Modifier.fillMaxWidth()) {
                PagingImage(
                    modifier = Modifier.fillMaxWidth(),
                    imageList = stadium.images,
                    status = item.status,
                    onFriendClick = {
                        onFriendClick(item)
                    }
                )
                Column(
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextView(
                        text = stadium.name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = FontSize.EXTRA_REGULAR
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            TextView(text = stadium.rating)

                            Icon(
                                painter = painterResource(Resources.Icon.Star),
                                contentDescription = null,
                                tint = CustomThemeManager.colors.yellowColor
                            )
                        }

                        TextView(text = stadium.address)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp).padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(Resources.Icon.Calendar),
                            contentDescription = null,
                            tint = CustomThemeManager.colors.textColor.copy(alpha = 0.5f)
                        )

                        TextView(text = formatDateAndTime(item.datetimeRange.lower).first)
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(Resources.Icon.TimeSchedule),
                            contentDescription = null,
                            tint = CustomThemeManager.colors.textColor.copy(alpha = 0.5f)
                        )

                        TextView(text = formatDateAndTime(item.datetimeRange.lower).second)
                    }
                    Spacer(Modifier.weight(1f))

                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = CustomThemeManager.colors.mainColor,
                            contentColor = Color.White
                        ),
                        content = {
                            Icon(
                                painter = painterResource(Resources.Icon.Location),
                                contentDescription = null,
                            )
                        },
                        onClick = {
                            onRouteClick(item)
                        })
                }

            }
        }
    }
}