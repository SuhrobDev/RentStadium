package dev.soul.shared.theme.color


import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Stable
@Immutable
class CustomColors(
    screenBackground: Color,
    mainColor: Color,
    blurGreen: Color,
    textColor: Color,
    cardBackground: Color,
    selectedItem: Color,
    unselectedItem: Color,
    hintColor: Color,
    redBlur: Color,
    yellowBlur: Color,
    yellowColor: Color,
    borderColor: Color,
    lightGray: Color,
    greenColor: Color,
    blueColor: Color,
    blurBlueColor: Color,
    redColor: Color,
    blurRedColor: Color
) {
    var screenBackground by mutableStateOf(screenBackground)
        private set

    var mainColor by mutableStateOf(mainColor)
        private set

    var blurGreen by mutableStateOf(blurGreen)
        private set

    var textColor by mutableStateOf(textColor)
        private set

    var cardBackground by mutableStateOf(cardBackground)
        private set

    var selectedItem by mutableStateOf(selectedItem)
        private set

    var unselectedItem by mutableStateOf(unselectedItem)
        private set

    var hintColor by mutableStateOf(hintColor)
        private set

    var redBlur by mutableStateOf(redBlur)
        private set

    var yellowBlur by mutableStateOf(yellowBlur)
        private set

    var yellowColor by mutableStateOf(yellowColor)
        private set

    var borderColor by mutableStateOf(borderColor)
        private set

    var lightGray by mutableStateOf(lightGray)
        private set

    var greenColor by mutableStateOf(greenColor)
        private set

    var blueColor by mutableStateOf(blueColor)
        private set

    var blurBlueColor by mutableStateOf(blurBlueColor)
        private set

    var redColor by mutableStateOf(redColor)
        private set

    var blurRedColor by mutableStateOf(blurRedColor)
        private set

    fun updateColors(
        colors: CustomColors
    ) {

        screenBackground = colors.screenBackground

        mainColor = colors.mainColor

        blurGreen = colors.blurGreen

        textColor = colors.textColor

        cardBackground = colors.cardBackground

        selectedItem = colors.selectedItem

        unselectedItem = colors.unselectedItem

        hintColor = colors.hintColor

        redBlur = colors.redBlur

        yellowBlur = colors.yellowBlur

        yellowColor = colors.yellowColor

        borderColor = colors.borderColor

        lightGray = colors.lightGray

        greenColor = colors.greenColor

        blueColor = colors.blueColor

        blurBlueColor = colors.blurBlueColor

        redColor = colors.redColor

        blurRedColor = colors.blurRedColor

    }

    fun copy() = CustomColors(
        screenBackground = screenBackground,
        mainColor = mainColor,
        blurGreen = blurGreen,
        textColor = textColor,
        cardBackground = cardBackground,
        selectedItem = selectedItem,
        unselectedItem = unselectedItem,
        hintColor = hintColor,
        redBlur = redBlur,
        yellowBlur = yellowBlur,
        yellowColor = yellowColor,
        borderColor = borderColor,
        lightGray = lightGray,
        greenColor = greenColor,
        blueColor = blueColor,
        blurBlueColor = blurBlueColor,
        redColor = redColor,
        blurRedColor = blurRedColor
    )
}