package dev.soul.shared.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.soul.shared.theme.CustomThemeManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseModalBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    containerColor: Color = CustomThemeManager.colors.screenBackground,
    tonalElevation: Dp = 0.dp,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(
        0.dp
    ),
    dragHandle: @Composable (() -> Unit)? = null,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        content = {
            content()
        },
        dragHandle = dragHandle,
        tonalElevation = tonalElevation,
        shape = shape,
        containerColor = containerColor
    )
}