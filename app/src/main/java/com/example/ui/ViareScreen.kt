package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DefaultPlace
import com.example.model.ViareModes
import com.example.ui.components.ArrivalCard
import com.example.ui.components.DiscoveryPanel
import com.example.ui.components.ViareMapCanvas
import com.example.ui.theme.ViareBackground
import com.example.ui.theme.ViareBorder
import com.example.ui.theme.ViareCardBg
import com.example.ui.theme.ViareGreen
import com.example.ui.theme.ViareInk
import com.example.ui.theme.ViareSubtle
import kotlinx.coroutines.delay

@Composable
fun ViareScreen() {
    // Interactive state matching web app
    var query by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Explore") }
    var selectedMode by remember { mutableStateOf("Two-wheeler") }
    var isSaved by remember { mutableStateOf(false) }
    var isNavigating by remember { mutableStateOf(false) }
    var notice by remember { mutableStateOf("") }

    // Mobile view mode: "guide" or "map"
    var mobileViewMode by remember { mutableStateOf("guide") }

    // Auto-dismiss notice after 2.6 seconds
    LaunchedEffect(notice) {
        if (notice.isNotEmpty()) {
            delay(2600)
            notice = ""
        }
    }

    fun searchPlaces(searchTerm: String = query) {
        val trimmed = searchTerm.trim()
        if (trimmed.isEmpty()) return
        notice = "Showing the best of $trimmed"
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("viare_main_screen"),
        containerColor = ViareBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
        val navBarPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val isExpanded = maxWidth >= 760.dp
            val currentModeTime = ViareModes.find { it.name == selectedMode }?.time ?: "36 min"

            if (isExpanded) {
                // Wide layout: Side-by-Side (Discovery Panel left, Map right)
                Row(modifier = Modifier.fillMaxSize()) {
                    // Left: Discovery Sidebar
                    DiscoveryPanel(
                        query = query,
                        onQueryChange = { query = it },
                        onSearchSubmit = { searchPlaces() },
                        selectedCategory = category,
                        onCategorySelect = {
                            category = it
                            notice = "Browsing $it in Mathura"
                        },
                        selectedMode = selectedMode,
                        onModeSelect = { selectedMode = it },
                        isSaved = isSaved,
                        onSaveToggle = {
                            isSaved = !isSaved
                            notice = if (isSaved) "Place saved to your library" else "Removed from saved places"
                        },
                        isNavigating = isNavigating,
                        onNavigateToggle = {
                            isNavigating = !isNavigating
                            notice = if (isNavigating) "Navigation started to Kusum Sarovar" else "Navigation stopped"
                        },
                        onLocationShortcutClick = {
                            notice = "You’re exploring Mathura, Uttar Pradesh"
                        },
                        onDirectionsClick = {
                            notice = "Route options updated for current traffic"
                        },
                        onShareClick = {
                            notice = "Share link copied"
                        },
                        onFilterClick = {
                            notice = "Filters updated for Govardhan area"
                        },
                        modifier = Modifier
                            .width(410.dp)
                            .fillMaxHeight()
                            .padding(top = statusBarPadding, bottom = navBarPadding)
                    )

                    // Right: Map View with floating UI
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        // Interactive Map Canvas
                        ViareMapCanvas(
                            onSpotClick = { spot ->
                                notice = "Selected $spot"
                            },
                            onPoiClick = { poi ->
                                notice = "Selected $poi"
                            }
                        )

                        // Top Search Bar on Map
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = statusBarPadding + 20.dp)
                                .widthIn(max = 480.dp)
                                .fillMaxWidth(0.85f)
                                .height(48.dp)
                                .shadow(8.dp, RoundedCornerShape(13.dp), spotColor = Color(0x1F273B2D))
                                .clip(RoundedCornerShape(13.dp))
                                .border(1.dp, Color(0xFFE8E8DC), RoundedCornerShape(13.dp))
                                .background(ViareCardBg)
                                .padding(horizontal = 14.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = null,
                                    tint = Color(0xFF738078),
                                    modifier = Modifier.size(18.dp)
                                )

                                BasicTextField(
                                    value = query,
                                    onValueChange = { query = it },
                                    modifier = Modifier.weight(1f),
                                    textStyle = TextStyle(
                                        fontSize = 12.sp,
                                        color = ViareInk,
                                        fontFamily = FontFamily.Default
                                    ),
                                    cursorBrush = SolidColor(ViareGreen),
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                    keyboardActions = KeyboardActions(onSearch = { searchPlaces() }),
                                    decorationBox = { innerTextField ->
                                        if (query.isEmpty()) {
                                            Text(
                                                text = "Where do you want to go?",
                                                fontSize = 12.sp,
                                                color = Color(0xFF9AA29D)
                                            )
                                        }
                                        innerTextField()
                                    }
                                )

                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(RoundedCornerShape(9.dp))
                                        .background(ViareGreen)
                                        .clickable { searchPlaces() },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowOutward,
                                        contentDescription = "Search map",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        // Map Floating Tools (Right side)
                        Column(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = statusBarPadding + 85.dp, end = 22.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .shadow(4.dp, RoundedCornerShape(12.dp), spotColor = Color(0x1F273B2D))
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(1.dp, Color(0xFFE6E7DB), RoundedCornerShape(12.dp))
                                    .background(ViareCardBg)
                                    .clickable { notice = "Centered on your location" }
                                    .testTag("map_current_location_button"),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.MyLocation,
                                    contentDescription = "Current location",
                                    tint = Color(0xFF52665A),
                                    modifier = Modifier.size(19.dp)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .shadow(4.dp, RoundedCornerShape(12.dp), spotColor = Color(0x1F273B2D))
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(1.dp, Color(0xFFE6E7DB), RoundedCornerShape(12.dp))
                                    .background(ViareCardBg)
                                    .clickable { notice = "Share link copied" }
                                    .testTag("map_share_button"),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Share,
                                    contentDescription = "Share map",
                                    tint = Color(0xFF52665A),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        // Map Brand Watermark (Bottom Left)
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(start = 20.dp, bottom = navBarPadding + 18.dp)
                                .shadow(4.dp, RoundedCornerShape(10.dp), spotColor = Color(0x14273B2D))
                                .clip(RoundedCornerShape(10.dp))
                                .border(1.dp, Color(0xFFE7E7DB), RoundedCornerShape(10.dp))
                                .background(Color(0xF0FFFEFA))
                                .padding(horizontal = 10.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(ViareGreen),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Map,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(15.dp)
                                )
                            }

                            Column {
                                Text(
                                    text = "viare",
                                    fontSize = 12.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ViareInk
                                )
                                Text(
                                    text = "Your world. A wiser way.",
                                    fontSize = 7.5.sp,
                                    color = Color(0xFF8A958D)
                                )
                            }
                        }

                        // Map Attribution (Bottom Right)
                        Text(
                            text = "Map data © OpenStreetMap contributors",
                            fontSize = 8.sp,
                            color = Color(0xFF768275),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 16.dp, bottom = navBarPadding + 14.dp)
                        )

                        // Floating Arrival Card (Docked Bottom Right)
                        ArrivalCard(
                            place = DefaultPlace,
                            selectedMode = selectedMode,
                            modeTime = currentModeTime,
                            isSaved = isSaved,
                            onSaveToggle = {
                                isSaved = !isSaved
                                notice = if (isSaved) "Place saved to your library" else "Removed from saved places"
                            },
                            onUseModeClick = {
                                selectedMode = "Two-wheeler"
                                notice = "Switched to Two-wheeler navigation"
                            },
                            onCardClick = {
                                notice = "Viewing Kusum Sarovar arrival details"
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 24.dp, bottom = navBarPadding + 36.dp)
                                .width(340.dp)
                        )
                    }
                }
            } else {
                // Mobile layout: Seamless Tabbed / Interactive View
                Box(modifier = Modifier.fillMaxSize()) {
                    if (mobileViewMode == "map") {
                        // Fullscreen Interactive Map
                        ViareMapCanvas(
                            onSpotClick = { spot ->
                                notice = "Selected $spot"
                            },
                            onPoiClick = { poi ->
                                notice = "Selected $poi"
                            }
                        )

                        // Map Search Bar on Phone
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = statusBarPadding + 12.dp, start = 14.dp, end = 14.dp)
                                .fillMaxWidth()
                                .height(46.dp)
                                .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = Color(0x1F273B2D))
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFE8E8DC), RoundedCornerShape(12.dp))
                                .background(ViareCardBg)
                                .padding(horizontal = 12.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = null,
                                    tint = Color(0xFF738078),
                                    modifier = Modifier.size(17.dp)
                                )

                                BasicTextField(
                                    value = query,
                                    onValueChange = { query = it },
                                    modifier = Modifier.weight(1f),
                                    textStyle = TextStyle(
                                        fontSize = 12.sp,
                                        color = ViareInk
                                    ),
                                    cursorBrush = SolidColor(ViareGreen),
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                    keyboardActions = KeyboardActions(onSearch = { searchPlaces() }),
                                    decorationBox = { innerTextField ->
                                        if (query.isEmpty()) {
                                            Text(
                                                text = "Where do you want to go?",
                                                fontSize = 12.sp,
                                                color = Color(0xFF9AA29D)
                                            )
                                        }
                                        innerTextField()
                                    }
                                )

                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(ViareGreen)
                                        .clickable { searchPlaces() },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowOutward,
                                        contentDescription = "Search",
                                        tint = Color.White,
                                        modifier = Modifier.size(15.dp)
                                    )
                                }
                            }
                        }

                        // Map Floating Tools
                        Column(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = statusBarPadding + 70.dp, end = 14.dp),
                            verticalArrangement = Arrangement.spacedBy(7.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .shadow(4.dp, RoundedCornerShape(10.dp), spotColor = Color(0x1F273B2D))
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(1.dp, Color(0xFFE6E7DB), RoundedCornerShape(10.dp))
                                    .background(ViareCardBg)
                                    .clickable { notice = "Centered on your location" },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.MyLocation,
                                    contentDescription = "Current location",
                                    tint = Color(0xFF52665A),
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .shadow(4.dp, RoundedCornerShape(10.dp), spotColor = Color(0x1F273B2D))
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(1.dp, Color(0xFFE6E7DB), RoundedCornerShape(10.dp))
                                    .background(ViareCardBg)
                                    .clickable { notice = "Share link copied" },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Share,
                                    contentDescription = "Share map",
                                    tint = Color(0xFF52665A),
                                    modifier = Modifier.size(17.dp)
                                )
                            }
                        }

                        // Mobile Arrival Card
                        ArrivalCard(
                            place = DefaultPlace,
                            selectedMode = selectedMode,
                            modeTime = currentModeTime,
                            isSaved = isSaved,
                            onSaveToggle = {
                                isSaved = !isSaved
                                notice = if (isSaved) "Place saved to your library" else "Removed from saved places"
                            },
                            onUseModeClick = {
                                selectedMode = "Two-wheeler"
                                notice = "Switched to Two-wheeler navigation"
                            },
                            onCardClick = {
                                mobileViewMode = "guide"
                            },
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(start = 14.dp, end = 14.dp, bottom = navBarPadding + 68.dp)
                                .fillMaxWidth()
                        )

                        // Attribution
                        Text(
                            text = "Map data © OpenStreetMap contributors",
                            fontSize = 8.sp,
                            color = Color(0xFF768275),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 14.dp, bottom = navBarPadding + 52.dp)
                        )
                    } else {
                        // Mobile Discovery Panel
                        DiscoveryPanel(
                            query = query,
                            onQueryChange = { query = it },
                            onSearchSubmit = { searchPlaces() },
                            selectedCategory = category,
                            onCategorySelect = {
                                category = it
                                notice = "Browsing $it in Mathura"
                            },
                            selectedMode = selectedMode,
                            onModeSelect = { selectedMode = it },
                            isSaved = isSaved,
                            onSaveToggle = {
                                isSaved = !isSaved
                                notice = if (isSaved) "Place saved to your library" else "Removed from saved places"
                            },
                            isNavigating = isNavigating,
                            onNavigateToggle = {
                                isNavigating = !isNavigating
                                notice = if (isNavigating) "Navigation started to Kusum Sarovar" else "Navigation stopped"
                            },
                            onLocationShortcutClick = {
                                notice = "You’re exploring Mathura, Uttar Pradesh"
                            },
                            onDirectionsClick = {
                                mobileViewMode = "map"
                                notice = "Showing interactive route on map"
                            },
                            onShareClick = {
                                notice = "Share link copied"
                            },
                            onFilterClick = {
                                notice = "Filters updated for Govardhan area"
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = statusBarPadding, bottom = navBarPadding + 58.dp)
                        )
                    }

                    // Mobile Floating Bottom Navigation Switcher
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = navBarPadding + 12.dp)
                            .shadow(12.dp, RoundedCornerShape(99.dp), spotColor = Color(0x33273B2D))
                            .clip(RoundedCornerShape(99.dp))
                            .border(1.dp, Color(0xFFE6E7DB), RoundedCornerShape(99.dp))
                            .background(Color(0xF5FFFEFA))
                            .padding(4.dp)
                            .testTag("mobile_view_switcher")
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            // Guide Tab
                            val isGuide = mobileViewMode == "guide"
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(99.dp))
                                    .background(if (isGuide) ViareGreen else Color.Transparent)
                                    .clickable { mobileViewMode = "guide" }
                                    .padding(horizontal = 14.dp, vertical = 7.dp)
                                    .testTag("tab_guide"),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Explore,
                                    contentDescription = null,
                                    tint = if (isGuide) Color.White else ViareSubtle,
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = "Guide",
                                    fontSize = 11.sp,
                                    fontWeight = if (isGuide) FontWeight.SemiBold else FontWeight.Medium,
                                    color = if (isGuide) Color.White else ViareSubtle
                                )
                            }

                            // Live Map Tab
                            val isMap = mobileViewMode == "map"
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(99.dp))
                                    .background(if (isMap) ViareGreen else Color.Transparent)
                                    .clickable { mobileViewMode = "map" }
                                    .padding(horizontal = 14.dp, vertical = 7.dp)
                                    .testTag("tab_map"),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Map,
                                    contentDescription = null,
                                    tint = if (isMap) Color.White else ViareSubtle,
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = "Live Map",
                                    fontSize = 11.sp,
                                    fontWeight = if (isMap) FontWeight.SemiBold else FontWeight.Medium,
                                    color = if (isMap) Color.White else ViareSubtle
                                )
                            }
                        }
                    }
                }
            }

            // Global Toast Notification (Matches `.toast-note` in web Viare)
            AnimatedVisibility(
                visible = notice.isNotEmpty(),
                enter = fadeIn() + slideInVertically(initialOffsetY = { -it / 2 }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { -it / 2 }),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = statusBarPadding + 76.dp)
            ) {
                Row(
                    modifier = Modifier
                        .shadow(10.dp, RoundedCornerShape(10.dp), spotColor = Color(0x2E26372A))
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Color(0xFFE5E8DE), RoundedCornerShape(10.dp))
                        .background(Color(0xFFFFFEFA))
                        .padding(horizontal = 13.dp, vertical = 10.dp)
                        .testTag("toast_notice"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = Color(0xFF405C49),
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = notice,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF405C49)
                    )

                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clickable { notice = "" },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Dismiss",
                            tint = Color(0xFF7A887E),
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
            }
        }
    }
}
