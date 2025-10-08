package dev.soul.stadium_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
actual fun StadiumLocationMapComponent(lat: Double, lng: Double) {
    val initialCoordinates = LatLng(lat, lng)
    
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialCoordinates, 13f)
    }

    val markerState = rememberMarkerState(
        position = LatLng(lat, lng)
    )

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(165.dp)
            .background(shape = RoundedCornerShape(20.dp), color = Color.Transparent)
            .clip(RoundedCornerShape(20.dp)),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            zoomGesturesEnabled = false,
            scrollGesturesEnabled = false,
            rotationGesturesEnabled = false,
            tiltGesturesEnabled = false,
            myLocationButtonEnabled = false,
            mapToolbarEnabled = false
        ),
        properties = MapProperties(
            isMyLocationEnabled = false
        )
    ) {
        Marker(
            state = markerState,
            title = "",
            snippet = "",
            onClick = {
                false
            }
        )
    }
}