package dev.soul.schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.soul.domain.model.user.schedule.response.ScheduleStatus
import dev.soul.shared.components.TextView
import dev.soul.shared.semiBoldFont
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun StatusBadge(
    modifier: Modifier = Modifier,
    status: ScheduleStatus,
) {
    TextView(
        modifier = modifier
            .background(status.toUI().backgroundColor, shape = RoundedCornerShape(50.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        text = status.toUI().text,
        textColor = status.toUI().textColor,
        fontFamily = semiBoldFont()
    )
}

data class StatusUI(
    val text: String,
    val backgroundColor: Color,
    val textColor: Color,
)

@Composable
fun ScheduleStatus.toUI(): StatusUI = when (this) {
    ScheduleStatus.PENDING -> StatusUI(
        text = "Pending",
        backgroundColor = CustomThemeManager.colors.yellowColor, // amber
        textColor = Color.White
    )

    ScheduleStatus.ACCEPTED -> StatusUI(
        text = "Accepted",
        backgroundColor = CustomThemeManager.colors.mainColor, // green
        textColor = Color.White
    )

    ScheduleStatus.CANCELLED -> StatusUI(
        text = "Cancelled",
        backgroundColor = CustomThemeManager.colors.redColor, // red
        textColor = Color.White
    )

    ScheduleStatus.REJECTED -> StatusUI(
        text = "Rejected",
        backgroundColor = Color(0xFF9E9E9E), // gray
        textColor = Color.White
    )
}