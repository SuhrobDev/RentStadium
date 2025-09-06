package dev.soul.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.soul.shared.theme.CustomThemeManager
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBox(
    modifier: Modifier = Modifier,
    withToolBar: Boolean = true,
    toolBarTitle: String = "",
    isLoading: Boolean = false,
    backgroundColor: Color = CustomThemeManager.colors.baseScreenBackground,
    content: @Composable BoxScope.() -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            content()

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
//                        .background(CustomThemeManager.colors.screenBackground.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(trackColor = CustomThemeManager.colors.mainColor)
                }
            }
        }

    }

}

@Preview
@Composable
fun BaseStackPreview() {
    BaseBox(
        toolBarTitle = "Title",
    ) {

    }
}