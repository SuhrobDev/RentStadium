package dev.soul.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.soul.domain.model.user.search.maps.StadiumModel
import dev.soul.domain.model.user.search.response.StadiumItemModel
import dev.soul.shared.Resources
import org.jetbrains.compose.resources.painterResource

@Composable
fun SearchResultItem(
    stadiumModel: StadiumItemModel,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(stadiumModel.id) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Stadium Image
        AsyncImage(
            model = stadiumModel.images.first(),
            contentDescription = stadiumModel.name,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Name + Address
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stadiumModel.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = stadiumModel.address,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Right arrow
        Icon(
            painter = painterResource(Resources.Icon.RightArrow),
            contentDescription = "Go",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}