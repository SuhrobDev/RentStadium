package dev.soul.stadium_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.soul.shared.Resources
import dev.soul.shared.theme.CustomThemeManager
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ActionSection(
    modifier: Modifier = Modifier,
    onMapClick: () -> Unit,
    onFriendClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionItem(
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            onClick = onMapClick,
            icon = Resources.Icon.Map,
        )

        ActionItem(
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            onClick = onFriendClick,
            icon = Resources.Icon.AddFriend,
        )

        ActionItem(
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            onClick = onSaveClick,
            icon = Resources.Icon.Save,
        )
    }
}

@Composable
fun ActionItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: DrawableResource
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = CustomThemeManager.colors.mainColor
        ),
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
            )
        }
    )
}