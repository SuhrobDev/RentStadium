package dev.soul.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.soul.shared.FontSize
import dev.soul.shared.Resources
import dev.soul.shared.theme.CustomThemeManager
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseToolbar(
    modifier: Modifier = Modifier,
    name: String,
    onBack: () -> Unit,
    menu1: DrawableResource? = null,
    menu2: DrawableResource? = null,
    onMenu1: () -> Unit = {},
    onMenu2: () -> Unit = {},
    containerColor: Color = CustomThemeManager.colors.screenBackground
) {
    TopAppBar(
        modifier = modifier, title = {
            TextView(
                text = name,
                textColor = CustomThemeManager.colors.textColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = FontSize.MEDIUM,
                fontWeight = FontWeight.Medium
            )
        }, navigationIcon = {
            IconButton(
                onClick = onBack,
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(Resources.Icon.LeftArrow),
                    contentDescription = "back button",
                    tint = CustomThemeManager.colors.textColor
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = containerColor),
        actions = {
            if (menu1 == null && menu2 == null)
                Text("              ")
            else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    menu1?.let {
                        IconButton(onClick = {
                            onMenu1()
                        }) {
                            Icon(
                                painter = painterResource(menu1),
                                contentDescription = null,
                                tint = CustomThemeManager.colors.textColor
                            )
                        }
                    }
                    menu2?.let {
                        IconButton(onClick = {
                            onMenu2()
                        }) {
                            Icon(
                                painter = painterResource(menu2),
                                contentDescription = null,
                                tint = CustomThemeManager.colors.textColor
                            )
                        }
                    }
                }
            }
        }
    )
}

//@HotPreview
@Composable
private fun PreviewBaseToolbar() {
    BaseToolbar(name = "Список по фото", onBack = {})
}