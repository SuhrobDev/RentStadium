package dev.soul.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.soul.shared.FontSize
import dev.soul.shared.Resources
import dev.soul.shared.theme.CustomThemeManager
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Immutable
data class ToastStyle(
    val color: Color,
    val title: String,
    val icon: DrawableResource
)

enum class ToastStatus {
    SUCCESS,
    ERROR,
    INFO,
    WARNING
}

@Composable
fun CustomToast(
    modifier: Modifier = Modifier,
    status: ToastStatus,
    message: String,
    onDismiss: () -> Unit,
) {
    val toastStyle = getToastStyle(status)

    Surface(
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 6.dp,
        shadowElevation = 6.dp,
        color = CustomThemeManager.colors.screenBackground,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(72.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp)),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .fillMaxHeight()
                    .background(color = toastStyle.color, shape = RoundedCornerShape(2.dp))
            )

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(color = toastStyle.color, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(toastStyle.icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                TextView(
                    text = toastStyle.title,
                    fontSize = FontSize.EXTRA_MEDIUM,
                    fontWeight = FontWeight.SemiBold,
                    textColor = toastStyle.color
                )

                TextView(
                    text = message,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .width(32.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            color = CustomThemeManager.colors.lightGray,
                            CircleShape
                        )
                        .clip(CircleShape)
                        .clickable {
                            onDismiss()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Resources.Icon.X),
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
            }
        }
    }
}