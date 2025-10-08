package dev.soul.search.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import dev.soul.domain.model.user.search.maps.StadiumModel
import dev.soul.search.ui.map.components.MapStadiumItem
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
actual fun MapComponent(
    stadiums: List<StadiumModel>,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onStadiumSelected: (StadiumModel) -> Unit,
    onMapMoved: (Double, Double) -> Unit
) {
    var selectedStadium by remember { mutableStateOf<StadiumModel?>(null) }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val density = LocalDensity.current
    val screenHeightPx = with(density) { screenHeight.toPx() }

    var offsetY by remember { mutableFloatStateOf(0f) }

    val animatedOffset by animateFloatAsState(
        targetValue = offsetY,
        finishedListener = {
            if (it >= screenHeightPx * 0.2f)
                selectedStadium = null
            else
                offsetY = 0f
        },
        label = "dragOffset"
    )
    val initialCoordinates = LatLng(41.330162, 69.285203)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialCoordinates, 13f)
    }
    // Detect when user moves or zooms map
    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            onMapMoved(cameraPositionState.position.target.latitude, cameraPositionState.position.target.longitude  )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {


        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = false,
                mapToolbarEnabled = false
            ),
            properties = MapProperties(
                isMyLocationEnabled = true
            )
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
                        offsetY = 0f
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
                    .offset { IntOffset(0, animatedOffset.roundToInt()) }
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->
                            offsetY = (offsetY + delta).coerceAtLeast(0f)
                        },
                        onDragStopped = {
                            offsetY = if (offsetY > screenHeightPx * 0.2f)
                                screenHeightPx
                            else
                                0f
                        }
                    )
            ) {
                MapStadiumItem(
                    modifier = Modifier.clipToBounds().clickable{
                        onStadiumSelected(stadium)
                    },
                    stadium, onClose = { selectedStadium = null },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope
                )
            }
        }
    }
}