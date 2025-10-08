package dev.soul.stadium_detail

import androidx.compose.runtime.Composable

@Composable
expect fun StadiumLocationMapComponent(
    lat: Double,
    lng: Double
)