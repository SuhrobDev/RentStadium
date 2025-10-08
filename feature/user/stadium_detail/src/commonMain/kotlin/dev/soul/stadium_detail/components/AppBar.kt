package dev.soul.stadium_detail.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.soul.shared.Resources
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    onShare: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                modifier = Modifier,
                onClick = onNavigationClick,
                content = {
                    Icon(
                        painter = painterResource(Resources.Icon.LeftArrow),
                        contentDescription = null
                    )
                })
        },
        title = {},
        modifier = modifier,
        actions = {
            IconButton(
                modifier = Modifier,
                onClick = onShare,
                content = {
                    Icon(
                        painter = painterResource(Resources.Icon.Share),
                        contentDescription = null
                    )
                })
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
    )
}