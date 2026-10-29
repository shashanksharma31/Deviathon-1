package com.example.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.ui.graphics.vector.ImageVector

data class TransportMode(
    val name: String,
    val time: String,
    val icon: ImageVector
)

val ViareModes = listOf(
    TransportMode("Drive", "38 min", Icons.Filled.DirectionsCar),
    TransportMode("Two-wheeler", "36 min", Icons.Filled.TwoWheeler),
    TransportMode("Transit", "1 h 12", Icons.Filled.DirectionsBus),
    TransportMode("Walk", "4 h 50", Icons.Filled.DirectionsWalk)
)

val ViareCategories = listOf(
    "Explore",
    "Restaurants",
    "Cafés",
    "Petrol",
    "Hotels"
)

data class PhotoItem(
    val id: String,
    val label: String
)

val KusumSarovarPhotos = listOf(
    PhotoItem("photo_one", "THE QUIET GHATS"),
    PhotoItem("photo_two", "SANDSTONE CHHATRIS"),
    PhotoItem("photo_three", "STILL WATER")
)

data class PlaceInfo(
    val title: String,
    val locality: String,
    val category: String,
    val address: String,
    val rating: Float,
    val reviewCount: String,
    val isOpen: Boolean,
    val description: String,
    val distance: String,
    val viaRoad: String,
    val routeInsight: String
)

val DefaultPlace = PlaceInfo(
    title = "Kusum Sarovar",
    locality = "Govardhan",
    category = "Heritage landmark",
    address = "Mathura–Govardhan Rd",
    rating = 4.7f,
    reviewCount = "(2,231 reviews)",
    isOpen = true,
    description = "Sandstone chhatris, quiet ghats and still water. A serene stop on the Govardhan Parikrama trail.",
    distance = "23 km",
    viaRoad = "via Mathura–Govardhan Rd",
    routeInsight = "Save 2 minutes with lighter traffic and easier access through Govardhan’s narrow lanes."
)
