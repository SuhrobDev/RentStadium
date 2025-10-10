package dev.soul.stadium_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import dev.soul.domain.model.user.available.response.AvailableModel
import dev.soul.domain.model.user.stadium_detail.response.StadiumDetailModel
import dev.soul.shared.FontSize
import dev.soul.shared.Resources
import dev.soul.shared.boldFont
import dev.soul.shared.components.BaseModalBottomSheet
import dev.soul.shared.components.ButtonView
import dev.soul.shared.components.TextView
import dev.soul.shared.mediumFont
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.shared.utils.formatPrice
import dev.soul.shared.utils.formatTime
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    loading: Boolean,
    stadiumDetail: StadiumDetailModel?,
    selectedAvailable: List<AvailableModel>,
    onConfirm: () -> Unit,
    onPrivacyClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    // Calculate unified time range
    val (startTime, endTime) = calculateUnifiedTimeRange(selectedAvailable)

    BaseModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismiss = onDismiss,
        content = {
            BoxWithConstraints {
                val maxHeight = maxHeight * 0.95f
                Column(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(maxHeight)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header
                    TextView(
                        text = "Подтверждение бронирования",
                        textColor = CustomThemeManager.colors.textColor,
                        fontSize = FontSize.EXTRA_MEDIUM,
                        fontWeight = FontWeight.Bold,
                        fontFamily = boldFont()
                    )

                    HorizontalDivider(
                        color = CustomThemeManager.colors.textColor.copy(alpha = 0.1f),
                        thickness = 1.dp
                    )

                    // Stadium Information Section
                    stadiumDetail?.let { stadium ->
                        StadiumInfoSection(stadium = stadium)
                    }

                    HorizontalDivider(
                        color = CustomThemeManager.colors.textColor.copy(alpha = 0.1f),
                        thickness = 1.dp
                    )

                    // Booking Details Section
                    BookingDetailsSection(
                        startTime = startTime,
                        endTime = endTime,
                        selectedSlots = selectedAvailable,
                        price = stadiumDetail?.price ?: ""
                    )

                    HorizontalDivider(
                        color = CustomThemeManager.colors.textColor.copy(alpha = 0.1f),
                        thickness = 1.dp
                    )

                    // Stadium Rules Section
                    StadiumRulesSection()

                    HorizontalDivider(
                        color = CustomThemeManager.colors.textColor.copy(alpha = 0.1f),
                        thickness = 1.dp
                    )

                    // Privacy Policy Agreement
                    PrivacyPolicySection(onClick = onPrivacyClick)

                    Spacer(modifier = Modifier.height(8.dp))

                    // Confirm Button
                    ButtonView(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Подтвердить бронирование",
                        textColor = Color.White,
                        isLoading = loading,
                        onClick = onConfirm
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    )
}

@Composable
private fun StadiumInfoSection(stadium: StadiumDetailModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextView(
            text = "Информация о стадионе",
            textColor = CustomThemeManager.colors.textColor,
            fontSize = FontSize.MEDIUM,
            fontWeight = FontWeight.SemiBold,
            fontFamily = mediumFont()
        )

        // Stadium Name
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Resources.Icon.Ball),
                contentDescription = null,
                tint = CustomThemeManager.colors.mainColor,
                modifier = Modifier.size(20.dp)
            )
            TextView(
                text = stadium.name,
                textColor = CustomThemeManager.colors.textColor,
                fontSize = FontSize.REGULAR,
                fontWeight = FontWeight.Medium
            )
        }

        // Address
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                painter = painterResource(Resources.Icon.Location),
                contentDescription = null,
                tint = CustomThemeManager.colors.mainColor,
                modifier = Modifier.size(20.dp)
            )
            TextView(
                text = stadium.address,
                textColor = CustomThemeManager.colors.textColor.copy(alpha = 0.7f),
                fontSize = FontSize.SMALL,
                modifier = Modifier.weight(1f)
            )
        }

        // Rating
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Resources.Icon.Star),
                contentDescription = null,
                tint = CustomThemeManager.colors.mainColor,
                modifier = Modifier.size(20.dp)
            )
            TextView(
                text = "Рейтинг: ${stadium.rating}",
                textColor = CustomThemeManager.colors.textColor,
                fontSize = FontSize.REGULAR
            )
        }
    }
}

@Composable
private fun BookingDetailsSection(
    startTime: String,
    endTime: String,
    selectedSlots: List<AvailableModel>,
    price: String
) {
    // Calculate total price (multiply by slot count)
    val pricePerSlot = price.replace(" ", "").replace(",", "").toDoubleOrNull() ?: 0.0
    val totalPrice = pricePerSlot * selectedSlots.size
    val formattedTotalPrice = /*formatPrice(totalPrice)*/totalPrice

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                CustomThemeManager.colors.mainColor.copy(alpha = 0.05f),
                RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextView(
            text = "Детали бронирования",
            textColor = CustomThemeManager.colors.textColor,
            fontSize = FontSize.MEDIUM,
            fontWeight = FontWeight.SemiBold,
            fontFamily = mediumFont()
        )

        // Unified Time Range
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Resources.Icon.Time),
                    contentDescription = null,
                    tint = CustomThemeManager.colors.mainColor,
                    modifier = Modifier.size(20.dp)
                )
                TextView(
                    text = "Время:",
                    textColor = CustomThemeManager.colors.textColor,
                    fontSize = FontSize.REGULAR
                )
            }
            TextView(
                text = "$startTime - $endTime",
                textColor = CustomThemeManager.colors.textColor,
                fontSize = FontSize.REGULAR,
                fontWeight = FontWeight.Bold,
                fontFamily = boldFont()
            )
        }

        // Number of slots
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Resources.Icon.Ticket),
                    contentDescription = null,
                    tint = CustomThemeManager.colors.mainColor,
                    modifier = Modifier.size(20.dp)
                )
                TextView(
                    text = "Количество слотов:",
                    textColor = CustomThemeManager.colors.textColor,
                    fontSize = FontSize.REGULAR
                )
            }
            TextView(
                text = "${selectedSlots.size}",
                textColor = CustomThemeManager.colors.textColor,
                fontSize = FontSize.REGULAR,
                fontWeight = FontWeight.Bold,
                fontFamily = boldFont()
            )
        }

        // Date
        selectedSlots.firstOrNull()?.let { firstSlot ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Resources.Icon.Location),
                        contentDescription = null,
                        tint = CustomThemeManager.colors.mainColor,
                        modifier = Modifier.size(20.dp)
                    )
                    TextView(
                        text = "День недели:",
                        textColor = CustomThemeManager.colors.textColor,
                        fontSize = FontSize.REGULAR
                    )
                }
                TextView(
                    text = firstSlot.dayOfWeekDisplay,
                    textColor = CustomThemeManager.colors.textColor,
                    fontSize = FontSize.REGULAR,
                    fontWeight = FontWeight.Bold,
                    fontFamily = boldFont()
                )
            }
        }

        HorizontalDivider(
            color = CustomThemeManager.colors.textColor.copy(alpha = 0.1f),
            thickness = 1.dp
        )

        // Total Price
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextView(
                text = "Итого:",
                textColor = CustomThemeManager.colors.textColor,
                fontSize = FontSize.MEDIUM,
                fontWeight = FontWeight.Bold,
                fontFamily = boldFont()
            )
            TextView(
                text = "$formattedTotalPrice сум",
                textColor = CustomThemeManager.colors.mainColor,
                fontSize = FontSize.EXTRA_MEDIUM,
                fontWeight = FontWeight.Bold,
                fontFamily = boldFont()
            )
        }
    }
}

@Composable
private fun StadiumRulesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Resources.Icon.Warning),
                contentDescription = null,
                tint = CustomThemeManager.colors.mainColor,
                modifier = Modifier.size(20.dp)
            )
            TextView(
                text = "Правила стадиона",
                textColor = CustomThemeManager.colors.textColor,
                fontSize = FontSize.MEDIUM,
                fontWeight = FontWeight.SemiBold,
                fontFamily = mediumFont()
            )
        }

        val rules = listOf(
            "Запрещается курить на территории стадиона",
            "Запрещается употреблять алкоголь",
            "Необходимо иметь при себе спортивную обувь",
            "Бронирование действительно в течение 15 минут после начала",
            "Отмена бронирования возможна за 24 часа"
        )

        rules.forEach { rule ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(start = 28.dp)
            ) {
                TextView(
                    text = "•",
                    textColor = CustomThemeManager.colors.textColor.copy(alpha = 0.7f),
                    fontSize = FontSize.REGULAR
                )
                TextView(
                    text = rule,
                    textColor = CustomThemeManager.colors.textColor.copy(alpha = 0.7f),
                    fontSize = FontSize.SMALL,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun PrivacyPolicySection(onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextView(
            text = "Нажимая \"Подтвердить\", вы соглашаетесь с ",
            textColor = CustomThemeManager.colors.textColor.copy(alpha = 0.6f),
            fontSize = FontSize.EXTRA_SMALL
        )
        TextView(
            text = "правилами стадиона",
            textColor = CustomThemeManager.colors.mainColor,
            fontSize = FontSize.EXTRA_SMALL,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { onClick() }
        )
    }
}

// Helper function to calculate unified time range
private fun calculateUnifiedTimeRange(selectedSlots: List<AvailableModel>): Pair<String, String> {
    if (selectedSlots.isEmpty()) return Pair("--:--", "--:--")

    // Sort slots by start time
    val sortedSlots = selectedSlots.sortedBy { it.startTime }

    val startTime = formatTime(sortedSlots.first().startTime)
    val endTime = formatTime(sortedSlots.last().endTime)

    return Pair(startTime, endTime)
}
