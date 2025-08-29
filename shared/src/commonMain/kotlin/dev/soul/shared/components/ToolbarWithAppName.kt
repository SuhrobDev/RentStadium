//package uz.datafin.memorkashback.android.core.designsystem.components
//
//import androidx.annotation.DrawableRes
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Devices
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import uz.datafin.memorkashback.SharedRes
//import uz.datafin.memorkashback.android.core.designsystem.theme.CustomThemeManager
//
//@Composable
//fun ToolbarWithAppName(
//    modifier: Modifier = Modifier,
//    onLeadingIconClick: () -> Unit,
//    onTrailingIconClick: () -> Unit,
//    containerColor: Color = CustomThemeManager.colors.screenBackground,
////    @DrawableRes trailingIcon: Int,
////    @DrawableRes leadingIcon: Int,
//) {
//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(56.dp)
//            .background(containerColor),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        IconButton(
//            onClick = onLeadingIconClick,
//            modifier = Modifier
//                .padding(start = 16.dp)
//                .background(
//                    shape = CircleShape,
//                    color = if (leadingIcon == SharedRes.images.ic_arrow_left.drawableResId)
//                        CustomThemeManager.colors.blurGreen
//                    else Color.Transparent
//                )
//        ) {
//            Icon(
//                painter = painterResource(leadingIcon),
//                contentDescription = null,
//                tint = CustomThemeManager.colors.mainColor,
//            )
//        }
//
////        Icon(
////            painter = painterResource(id = SharedRes.images.arava_name.drawableResId),
////            contentDescription = "logo",
////            tint = CustomThemeManager.colors.mainColor,
////            modifier = Modifier
////                .size(width = 90.dp, height = 21.dp)
////        )
//
//        IconButton(
//            onClick = onTrailingIconClick,
//            modifier = Modifier
//                .padding(end = 16.dp)
//                .background(
//                    shape = CircleShape,
//                    color = Color.Transparent
//                )
//        ) {
//            Icon(
//                painter = painterResource(trailingIcon),
//                contentDescription = null,
//                tint = CustomThemeManager.colors.mainColor,
//                modifier = Modifier.size(24.dp)
//            )
//        }
//    }
//}
//
//
//
//@Preview(showBackground = true, device = Devices.PIXEL_6, showSystemUi = true)
//@Composable
//private fun PreviewToolbarWithAppName() {
//    ToolbarWithAppName(
//        onLeadingIconClick = {},
//        onTrailingIconClick = {},
//        containerColor = Color.Transparent,
//        trailingIcon = SharedRes.images.ic_camera.drawableResId,
//        leadingIcon = SharedRes.images.ic_back.drawableResId
//    )
//}