package dev.soul.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.soul.shared.FontSize
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun PhoneNumberInput(
    modifier: Modifier = Modifier,
    phone: String,
    enabled: Boolean = true,
    onPhoneChange: (String) -> Unit
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier
                .background(
                    CustomThemeManager.colors.lightGray, shape = RoundedCornerShape(12.dp)
                )
                .padding(vertical = 16.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextView(text = "+998", fontSize = FontSize.REGULAR, textColor = Color(0xFFA09CAB))
        }

        PhoneNumberTextField(phone = phone, enabled = enabled) {
            onPhoneChange(it)
        }


    }
}