package dev.soul.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import dev.soul.domain.model.user.search.maps.StadiumModel
import dev.soul.search.ui.map.components.MapStadiumItem
import dev.soul.shared.theme.CustomThemeManager

@Composable
actual fun MapComponent(stadiums: List<StadiumModel>) {
    var selectedStadium by remember { mutableStateOf<StadiumModel?>(null) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val initialCoordinates = LatLng(41.330162, 69.285203)

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(initialCoordinates, 13f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            stadiums.forEach { stadium ->
                val markerState = rememberMarkerState(
                    position = LatLng(stadium.lat, stadium.long)
                )

                Marker(
                    state = markerState,
                    title = stadium.name,
                    snippet = stadium.address,
                    onClick = {
                        selectedStadium = stadium
                        false
                    }
                )
            }
        }

        selectedStadium?.let { stadium ->
            AnimatedVisibility(
                visible = true,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                MapStadiumItem(stadium, onClose = { selectedStadium = null })
            }
        }
    }
}