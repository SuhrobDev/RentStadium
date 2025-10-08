package dev.soul.stadium_detail.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.soul.shared.FontSize
import dev.soul.shared.Resources
import dev.soul.shared.components.TextView
import dev.soul.shared.mediumFont
import dev.soul.shared.theme.CustomThemeManager
import org.jetbrains.compose.resources.painterResource

@Composable
fun PriceSection(
    modifier: Modifier = Modifier,
    price: String,
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .border(width = 1.dp, color = Color.Gray.copy(alpha = 0.5f), shape = RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row {
            Icon(
                painter = painterResource(Resources.Icon.Price),
                contentDescription = null,
                tint = CustomThemeManager.colors.mainColor
            )

            Column(modifier = Modifier.padding(start = 6.dp)) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {

                    TextView(
                        text = "Стоимость аренды стадиона:",
                        textColor = CustomThemeManager.colors.textColor,
                        fontSize = FontSize.REGULAR
                    )
                }

                Row(modifier = Modifier) {
                    TextView(
                        text = price,
                        textColor = CustomThemeManager.colors.mainColor,
                        fontSize = FontSize.REGULAR,
                        fontFamily = mediumFont()
                    )

                    TextView(
                        text = "(в час)",
                        textColor = Color.Gray,
                        fontSize = FontSize.REGULAR,
                    )

                }
            }
        }


    }
}