package dev.soul.search.ui.map.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.soul.shared.FontSize
import dev.soul.shared.Resources
import dev.soul.shared.theme.CustomThemeManager
import org.jetbrains.compose.resources.painterResource

@Composable
fun FilterSection(
    filters: List<String>,
    selectedValues: Map<String, String>,
    onFilterSelected: (String, String) -> Unit
) {
    val filters = listOf("Category", "Sort", "Status")
    val (selected, setSelected) = remember { mutableStateOf("All") }

    LazyRow(
        modifier = Modifier
            .background(CustomThemeManager.colors.screenBackground)
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters) { filterName ->
            FilterItem(
                label = filterName,
                options = listOf("All", "Option 1", "Option 2"),
                selectedValue = selected,
                onSelected = setSelected,
                isSelected = selected != "All" // highlight only when not default
            )
        }
    }
}

@Composable
private fun FilterItem(
    label: String,
    options: List<String>,
    selectedValue: String,
    onSelected: (String) -> Unit,
    isSelected: Boolean,
    mainColor: Color = CustomThemeManager.colors.mainColor
) {
    var expanded by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = { expanded = true },
        modifier = Modifier
            .height(32.dp)
            .wrapContentWidth(),
        border = BorderStroke(1.dp, Color(0x52000000)),
        shape = RoundedCornerShape(36.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = if (isSelected) mainColor else Color.Black
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "$selectedValue",
                fontSize = FontSize.REGULAR,
                color = if (isSelected) mainColor else Color.Black
            )
            Icon(
                painter = painterResource(Resources.Icon.LeftArrow),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = if (isSelected) mainColor else Color.Black
            )
        }
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        options.forEach { option ->
            DropdownMenuItem(
                text = { Text(option) },
                onClick = {
                    onSelected(option)
                    expanded = false
                }
            )
        }
    }
}