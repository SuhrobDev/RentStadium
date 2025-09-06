package dev.soul.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.soul.shared.FontSize
import dev.soul.shared.components.TextView
import dev.soul.shared.mediumFont
import dev.soul.shared.semiBoldFont
import dev.soul.shared.theme.CustomThemeManager
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun StadiumType(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    text: String,
    distance: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CustomThemeManager.colors.screenBackground),
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = CustomThemeManager.colors.mainColor
            )
            Spacer(Modifier.height(16.dp))

            TextView(
                text = text,
                fontFamily = mediumFont(),
                lineHeight = 16.sp,
                fontSize = FontSize.REGULAR
            )

            TextView(
                text = distance,
                textColor = Color(0xFF99A2AD),
                lineHeight = 16.sp,
                fontSize = FontSize.SMALL
            )
        }
    }
}