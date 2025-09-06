package dev.soul.validation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import dev.soul.shared.FontSize
import dev.soul.shared.components.TextView
import dev.soul.shared.mediumFont
import dev.soul.shared.regularFont
import dev.soul.shared.semiBoldFont
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun SessionExpiredDialog(
    onRelogin: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(
                onClick = onRelogin,
                colors = ButtonDefaults.buttonColors(containerColor = CustomThemeManager.colors.mainColor)
            ) {
                TextView(
                    text = "Re-Login",
                    fontFamily = regularFont(),
                    fontSize = FontSize.EXTRA_REGULAR
                )
            }
        },
        title = {
            TextView(
                text = "Session Expired",
                fontFamily = semiBoldFont(),
                fontSize = FontSize.EXTRA_MEDIUM
            )
        },
        text = {
            TextView(
                text = "Your session has expired because your access token is invalid. Please log in again to continue.",
                fontFamily = mediumFont(),
                fontSize = FontSize.MEDIUM
            )
        },
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        containerColor = CustomThemeManager.colors.screenBackground,
    )
}