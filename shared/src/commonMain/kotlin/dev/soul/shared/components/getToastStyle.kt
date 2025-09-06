package dev.soul.shared.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.soul.shared.Resources
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun getToastStyle(status: ToastStatus): ToastStyle {
    return when (status) {
        ToastStatus.SUCCESS -> ToastStyle(
            color = CustomThemeManager.colors.greenColor, // green
            title = "Success",
            icon = Resources.Icon.Time
        )

        ToastStatus.ERROR -> ToastStyle(
            color = CustomThemeManager.colors.redColor, // red
            title = "Error",
            icon = Resources.Icon.Time
        )

        ToastStatus.INFO -> ToastStyle(
            color = CustomThemeManager.colors.blueColor, // blue
            title = "Info",
            icon = Resources.Icon.Time
        )

        ToastStatus.WARNING -> ToastStyle(
            color = Color(0xFFFF9800), // orange
            title = "Warning",
            icon = Resources.Icon.Time
        )
    }
}