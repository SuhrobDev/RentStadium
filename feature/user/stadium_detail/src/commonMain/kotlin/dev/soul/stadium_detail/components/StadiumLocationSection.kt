package dev.soul.stadium_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.soul.domain.model.user.stadium_detail.response.LocationModel
import dev.soul.shared.FontSize
import dev.soul.shared.Resources
import dev.soul.shared.boldFont
import dev.soul.shared.components.TextView
import dev.soul.shared.mediumFont
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.stadium_detail.StadiumLocationMapComponent
import org.jetbrains.compose.resources.painterResource

@Composable
fun StadiumLocationSection(
    modifier: Modifier = Modifier,
    locationModel: LocationModel,
    onNavigate: () -> Unit
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TextView(text = "Расположение", fontFamily = boldFont(), fontSize = FontSize.EXTRA_MEDIUM)

        StadiumLocationMapComponent(
            locationModel.coordinates.first(),
            locationModel.coordinates.last()
        )

        Column {
            TextView(
                text = "Ташкент, Чиланзар, 2-й кварт",
                fontFamily = mediumFont(),
                fontSize = FontSize.EXTRA_MEDIUM
            )
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(
                    painter = painterResource(Resources.Icon.Location),
                    contentDescription = null,
                    tint = Color.Gray
                )
                TextView(text = "13.3 км от вас", textColor = Color.Gray)
            }
        }

        TextButton(
            modifier = Modifier.padding(top = 16.dp),
            onClick = onNavigate, content = {
            TextView(
                text = "Построит маршрут",
                fontFamily = boldFont(),
                fontSize = FontSize.MEDIUM,
                textColor = CustomThemeManager.colors.mainColor
            )
        })
    }
}