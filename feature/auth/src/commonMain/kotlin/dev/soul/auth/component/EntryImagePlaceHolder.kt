package dev.soul.auth.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun EntryImagePlaceHolder(modifier: Modifier = Modifier, img: DrawableResource) {

    Image(
        modifier = modifier
            .fillMaxWidth()
            .height(266.dp)
            .padding(horizontal = 16.dp),
        painter = painterResource(resource =img),
        contentScale = ContentScale.Fit,
        contentDescription = null
    )

}