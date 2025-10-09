package dev.soul.stadium_detail.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.soul.domain.model.user.upcoming_days.response.UpcomingDaysModel
import dev.soul.shared.components.TextView
import dev.soul.shared.mediumFont
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun WeeksSection(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    weeks: List<UpcomingDaysModel>,
    onWeekClick: (Int) -> Unit,
) {

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        AnimatedTabIndicator(tabPositions, selectedTabIndex)
    }

    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        indicator = indicator,
        divider = {},
        containerColor = CustomThemeManager.colors.screenBackground
    ) {
        weeks.forEachIndexed { index, week ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onWeekClick(index) },
                text = {
                    WeekItem(
                        week = week.weekday,
                        isSelected = selectedTabIndex == index,
                        onClick = {
                            onWeekClick(index)
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun WeekItem(
    modifier: Modifier = Modifier,
    week: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    TextView(
        modifier = modifier.clickable {
            onClick()
        },
        text = week,
        fontFamily = mediumFont(),
        textColor = if (isSelected) CustomThemeManager.colors.textColor else CustomThemeManager.colors.textColor.copy(
            alpha = 0.5f
        )
    )
}

@Composable
fun AnimatedTabIndicator(
    tabPositions: List<TabPosition>,
    selectedTabIndex: Int
) {
    val transition = updateTransition(selectedTabIndex, label = "Tab indicator transition")

    val indicatorLeft by transition.animateDp(label = "Indicator left") {
        tabPositions[it].left
    }

    val indicatorRight by transition.animateDp(label = "Indicator right") {
        tabPositions[it].right
    }

    val color = CustomThemeManager.colors.mainColor

    Box(
        Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .height(3.dp)
            .background(color, shape = RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
    )
}