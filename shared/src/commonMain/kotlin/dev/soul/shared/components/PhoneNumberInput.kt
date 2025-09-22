package dev.soul.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PhoneNumberInput(
    modifier: Modifier = Modifier,
    phone: String,
    enabled: Boolean = true,
    onPhoneChange: (String) -> Unit
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        PhoneNumberTextField(phone = phone, enabled = enabled) {
            onPhoneChange("+998$it")
        }
    }
}