package dev.soul.liked.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun StadiumItem(
    modifier: Modifier = Modifier,
    stadium: StadiumItemModel,
    onStadiumClick: (Int) -> Unit,
    onLiked: (Int, Boolean) -> Unit
) {
    Card(
        modifier = modifier,
        onClick = {
            onStadiumClick(stadium.id)
        },
        colors = CardDefaults.cardColors(containerColor = CustomThemeManager.colors.screenBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            PagingImage(
                modifier = Modifier.fillMaxWidth(),
                imageList = stadium.images,
                isLiked = stadium.liked,
                onLiked = {
                    onLiked(stadium.id, stadium.liked)
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
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}