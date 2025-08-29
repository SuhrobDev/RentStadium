package dev.soul.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import rentstadium.shared.generated.resources.Res
import rentstadium.shared.generated.resources.notosans_bold
import rentstadium.shared.generated.resources.notosans_extrabold
import rentstadium.shared.generated.resources.notosans_light
import rentstadium.shared.generated.resources.notosans_medium
import rentstadium.shared.generated.resources.notosans_regular
import rentstadium.shared.generated.resources.notosans_semibold

@Composable
fun extraBoldFont() = FontFamily(
    Font(Res.font.notosans_extrabold)
)

@Composable
fun boldFont() = FontFamily(
    Font(Res.font.notosans_bold)
)


@Composable
fun semiBoldFont() = FontFamily(
    Font(Res.font.notosans_semibold)
)

@Composable
fun mediumFont() = FontFamily(
    Font(Res.font.notosans_medium)
)


@Composable
fun regularFont() = FontFamily(
    Font(Res.font.notosans_regular)
)


@Composable
fun lightFont() = FontFamily(
    Font(Res.font.notosans_light)
)


object FontSize {
    val EXTRA_SMALL = 10.sp
    val SMALL = 12.sp
    val REGULAR = 14.sp
    val EXTRA_REGULAR = 16.sp
    val MEDIUM = 18.sp
    val EXTRA_MEDIUM = 20.sp
    val LARGE = 30.sp
    val EXTRA_LARGE = 40.sp
}