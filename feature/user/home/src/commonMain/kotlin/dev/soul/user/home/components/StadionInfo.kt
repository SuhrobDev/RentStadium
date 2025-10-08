package dev.soul.user.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.soul.domain.model.user.search.response.StadiumItemModel
import dev.soul.shared.FontSize
import dev.soul.shared.Resources
import dev.soul.shared.components.TextView
import dev.soul.shared.theme.CustomThemeManager
import org.jetbrains.compose.resources.painterResource

@Composable
fun StadionInfo(modifier: Modifier = Modifier,info: StadiumItemModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(horizontal = 12.dp)
    ) {
        TextView(
            text = info.name,
            fontWeight = FontWeight.SemiBold,
            fontSize = FontSize.EXTRA_REGULAR
        )

        TextView(
            text = "${info.price} сумов в час",
            textColor = CustomThemeManager.colors.mainColor,
        )

        TextView(
            text = info.address,
            textColor = CustomThemeManager.colors.textColor.copy(alpha = 0.7f),
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
                TextView(text = info.rating)

                Icon(
                    painter = painterResource(Resources.Icon.Star),
                    contentDescription = null,
                    tint = CustomThemeManager.colors.yellowColor
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(Resources.Icon.Location),
                    contentDescription = null,
                    tint = CustomThemeManager.colors.textColor.copy(alpha = 0.6f)
                )

                TextView(text = "0.3 km")

            }
        }
    }
}