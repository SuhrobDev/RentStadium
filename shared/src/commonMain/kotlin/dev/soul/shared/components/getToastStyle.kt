//package dev.soul.orderstadium.android.core.designsystem.components
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Color
//import dev.soul.orderstadium.SharedRes
//import dev.soul.orderstadium.android.core.designsystem.theme.CustomThemeManager
//import uz.datafin.memorkashback.SharedRes
//import uz.datafin.memorkashback.android.core.designsystem.theme.CustomThemeManager
//
//@Composable
//fun getToastStyle(status: ToastStatus): ToastStyle {
//    return when (status) {
//        ToastStatus.SUCCESS -> ToastStyle(
//            color = CustomThemeManager.colors.greenColor, // green
//            title = "Success",
//            icon = SharedRes.images.ic_tick.drawableResId
//        )
//
//        ToastStatus.ERROR -> ToastStyle(
//            color = CustomThemeManager.colors.redColor, // red
//            title = "Error",
//            icon = SharedRes.images.ic_error.drawableResId
//        )
//
//        ToastStatus.INFO -> ToastStyle(
//            color = CustomThemeManager.colors.blueColor, // blue
//            title = "Info",
//            icon = SharedRes.images.ic_info.drawableResId
//        )
//
//        ToastStatus.WARNING -> ToastStyle(
//            color = Color(0xFFFF9800), // orange
//            title = "Warning",
//            icon = SharedRes.images.ic_info.drawableResId
//        )
//    }
//}