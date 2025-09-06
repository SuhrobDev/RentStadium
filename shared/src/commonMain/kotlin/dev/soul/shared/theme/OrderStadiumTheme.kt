package dev.soul.shared.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import dev.soul.shared.theme.color.CustomColors
import dev.soul.shared.theme.color.CustomTheme

private val CustomLightColorScheme = CustomColors(
    screenBackground = ScreenBackgroundColorLight,
    baseScreenBackground = BaseScreenBackgroundColorLight,
    mainColor = MainGreenColor,
    blurGreen = BlurGreenColorLight,
    redBlur = RedBlurColorLight,
    yellowBlur = YellowBlurColorLight,
    textColor = TextColorLight,
    cardBackground = CardBackgroundColorLight,
    selectedItem = SelectedItemColorLight,
    unselectedItem = UnSelectedItemColorLight,
    hintColor = HintColorLight,
    yellowColor = YellowColorLight,
    borderColor = BorderColorLight,
    lightGray = LightGrayLight,
    greenColor = GreenColorLight,
    redColor = RedColorLight,
    blueColor = BlueColorLight,
    blurRedColor = BlurRedColorLight,
    blurBlueColor = BlurBlueColorLight,
    disabledButtonColor = DisabledButtonColorLight,
)

private val CustomDarkColorScheme = CustomColors(
    screenBackground = ScreenBackgroundColorDark,
    baseScreenBackground = BaseScreenBackgroundColorLight,
    mainColor = MainGreenColor,
    blurGreen = BlurGreenColorDark,
    redBlur = RedBlurColorDark,
    yellowBlur = YellowBlurColorDark,
    textColor = TextColorDark,
    cardBackground = CardBackgroundColorDark,
    selectedItem = SelectedItemColorDark,
    unselectedItem = UnSelectedItemColorDark,
    hintColor = HintColorDark,
    yellowColor = YellowColorDark,
    borderColor = BorderColorDark,
    lightGray = LightGrayDark,
    greenColor = GreenColorDark,
    redColor = RedColorDark,
    blueColor = BlueColorDark,
    blurRedColor = BlurRedColorDark,
    blurBlueColor = BlurBlueColorDark,
    disabledButtonColor = DisabledButtonColorLight
)

private val localProvider = staticCompositionLocalOf {
    CustomLightColorScheme
    CustomLightColorScheme
}

@Composable
private fun CustomLocalProvider(colors: CustomColors, content: @Composable () -> Unit) {
    val colorPalate = remember { colors.copy() }
    colorPalate.updateColors(colors)
    CompositionLocalProvider(localProvider provides colorPalate, content = content)
}

private val CustomTheme.colors: Pair<ColorScheme, CustomColors>
    @Composable
    get() = when (this) {
        CustomTheme.LIGHT -> Pair(
            lightColorScheme(),
            CustomLightColorScheme
        )

        CustomTheme.DARK -> Pair(
            darkColorScheme(),
            CustomLightColorScheme
        )

        CustomTheme.SYSTEM -> {
            if (isSystemInDarkTheme()) {
                Pair(darkColorScheme(), CustomLightColorScheme)
            } else {
                Pair(lightColorScheme(), CustomLightColorScheme)
            }
        }
    }

object CustomThemeManager {
    val colors: CustomColors
        @Composable
        get() = localProvider.current

    fun setThemeFromString(mode: String) {
        customTheme = when (mode.lowercase()) {
            "light" -> CustomTheme.LIGHT
            "dark" -> CustomTheme.DARK
            else -> CustomTheme.SYSTEM
        }
    }

    @Composable
    fun currentTheme(): CustomTheme {
        return when (customTheme) {
            CustomTheme.SYSTEM -> if (isSystemInDarkTheme()) CustomTheme.DARK else CustomTheme.LIGHT
            else -> customTheme
        }
    }

    var customTheme by mutableStateOf(CustomTheme.LIGHT)

    fun isSystemDark(): Boolean {
        return customTheme == CustomTheme.DARK
    }

}

@Composable
fun OrderStadiumTheme(
    custom: CustomTheme = CustomThemeManager.customTheme,
    content: @Composable () -> Unit
) {

    val (colorScheme, colors) = custom.colors
//    val systemUiController = rememberSystemUiController()

    SideEffect {
//        systemUiController.setStatusBarColor(
//            color = colors.screenBackground,
//            darkIcons = !CustomThemeManager.isSystemDark()
//        )
    }

    CustomLocalProvider(colors = colors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
