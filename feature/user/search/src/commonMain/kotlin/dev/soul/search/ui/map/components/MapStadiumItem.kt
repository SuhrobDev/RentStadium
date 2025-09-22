package dev.soul.search.ui.map.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.soul.domain.model.user.search.maps.StadiumModel
import dev.soul.shared.Resources
import dev.soul.shared.theme.CustomThemeManager
import org.jetbrains.compose.resources.painterResource

@Composable
fun MapStadiumItem(stadium: StadiumModel, onClose: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = CustomThemeManager.colors.screenBackground)
    ) {
        Column {

            PagingImage(
                modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                imageList = listOf(
                    Resources.Icon.Entry1,
                    Resources.Icon.Entry2,
                    Resources.Icon.Entry3
                ),
                onSaved = {

                }
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(stadium.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    "Price: \$${stadium.price}",
                    style = MaterialTheme.typography.bodySmall
                )
                Row {
                    Text("${stadium.rate} ⭐", style = MaterialTheme.typography.bodySmall)

                    Text(stadium.address, style = MaterialTheme.typography.bodyMedium)

                    Text(
                        "${stadium.distance} km from you",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(CustomThemeManager.colors.lightGray)
                    .padding(vertical = 8.dp)
            )
            Button(
                onClick = { onClose() },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomThemeManager.colors.screenBackground
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        4.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Close",
                        color = CustomThemeManager.colors.mainColor
                    )

                    Image(
                        painter = painterResource(Resources.Icon.RightArrow),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(CustomThemeManager.colors.mainColor),
                    )
                }
            }
        }
    }
}