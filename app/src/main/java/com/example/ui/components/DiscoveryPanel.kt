package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DefaultPlace
import com.example.model.PlaceInfo
import com.example.model.TransportMode
import com.example.model.ViareCategories
import com.example.model.ViareModes
import com.example.ui.theme.ViareAmber
import com.example.ui.theme.ViareBorder
import com.example.ui.theme.ViareGold
import com.example.ui.theme.ViareGreen
import com.example.ui.theme.ViareGreenDark
import com.example.ui.theme.ViareGreenSoft
import com.example.ui.theme.ViareInk
import com.example.ui.theme.ViareLine
import com.example.ui.theme.ViareLiveGreen
import com.example.ui.theme.ViareMuted
import com.example.ui.theme.ViarePanelBg
import com.example.ui.theme.ViareSubtle
import com.example.ui.theme.ViareSubtleLight

@Composable
fun DiscoveryPanel(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchSubmit: () -> Unit,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    selectedMode: String,
    onModeSelect: (String) -> Unit,
    isSaved: Boolean,
    onSaveToggle: () -> Unit,
    isNavigating: Boolean,
    onNavigateToggle: () -> Unit,
    onLocationShortcutClick: () -> Unit,
    onDirectionsClick: () -> Unit,
    onShareClick: () -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
    place: PlaceInfo = DefaultPlace
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .background(ViarePanelBg)
            .testTag("discovery_panel")
    ) {
        // 1. Brand Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .border(width = 1.dp, color = ViareLine)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Brand lockup
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(9.dp),
                modifier = Modifier.testTag("brand_lockup")
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(ViareGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Map,
                        contentDescription = "Viare Logo",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Text(
                    text = buildAnnotatedString {
                        append("viare")
                        withStyle(style = SpanStyle(color = ViareAmber)) {
                            append(".")
                        }
                    },
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-1.2).sp,
                    color = ViareInk
                )
            }

            // Live status & Menu
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(ViareLiveGreen, CircleShape)
                    )
                    Text(
                        text = "LIVE ROUTES",
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = ViareSubtle
                    )
                    Text(
                        text = "09:41",
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ViareInk
                    )
                }

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFFEBEAE2), RoundedCornerShape(8.dp))
                        .clickable { onFilterClick() }
                        .testTag("menu_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = ViareSubtle,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // 2. Scrollable Panel Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 22.dp, vertical = 20.dp)
        ) {
            // Eyebrow
            Text(
                text = "A LITTLE INTELLIGENCE. EVERY JOURNEY.",
                fontSize = 8.5.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.45.sp,
                color = ViareSubtleLight
            )

            // Main Display Headline
            Text(
                text = "Discover a little\nmore.",
                fontFamily = FontFamily.Serif,
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 36.sp,
                letterSpacing = (-1.1).sp,
                color = ViareInk,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            )

            Text(
                text = "Find your next favorite place, just around the bend.",
                fontSize = 11.5.sp,
                color = ViareSubtle,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Search Box
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, ViareBorder, RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = ViareSubtleLight,
                    modifier = Modifier.size(18.dp)
                )

                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("search_input"),
                    textStyle = TextStyle(
                        fontSize = 12.sp,
                        color = ViareInk,
                        fontFamily = FontFamily.Default
                    ),
                    cursorBrush = SolidColor(ViareGreen),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { onSearchSubmit() }),
                    decorationBox = { innerTextField ->
                        if (query.isEmpty()) {
                            Text(
                                text = "Search a place or ask Viare",
                                fontSize = 12.sp,
                                color = Color(0xFF9AA29D)
                            )
                        }
                        innerTextField()
                    }
                )

                if (query.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onQueryChange("") }
                            .testTag("search_clear_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Clear search",
                            tint = ViareSubtleLight,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(ViareGreen)
                        .clickable { onSearchSubmit() }
                        .testTag("search_submit_button"),
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

            // Location Shortcut
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLocationShortcutClick() }
                    .padding(vertical = 11.dp)
                    .testTag("location_shortcut"),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(ViareGreenSoft),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.MyLocation,
                        contentDescription = "Your Location",
                        tint = ViareGreen,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Your location",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ViareInk
                    )
                    Text(
                        text = "Mathura, Uttar Pradesh",
                        fontSize = 10.sp,
                        color = ViareSubtle,
                        modifier = Modifier.padding(top = 1.dp)
                    )
                }

                Icon(
                    imageVector = Icons.Filled.ArrowOutward,
                    contentDescription = null,
                    tint = Color(0xFF9AA49D),
                    modifier = Modifier.size(15.dp)
                )
            }

            // Category Chips Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(bottom = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                ViareCategories.forEach { cat ->
                    val isActive = cat == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(99.dp))
                            .border(
                                width = 1.dp,
                                color = if (isActive) ViareGreen else Color(0xFFE8E7DF),
                                shape = RoundedCornerShape(99.dp)
                            )
                            .background(if (isActive) ViareGreen else Color.White)
                            .clickable { onCategorySelect(cat) }
                            .padding(horizontal = 13.dp, vertical = 7.dp)
                            .testTag("category_chip_$cat")
                    ) {
                        Text(
                            text = cat,
                            fontSize = 10.sp,
                            fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isActive) Color.White else Color(0xFF657169)
                        )
                    }
                }
            }

            HorizontalDivider(color = ViareLine, thickness = 1.dp)

            // Place Summary ("YOUR NEXT STOP")
            Column(modifier = Modifier.padding(top = 14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "YOUR NEXT STOP",
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = ViareSubtleLight
                    )

                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .clickable { onFilterClick() }
                            .testTag("tune_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Tune,
                            contentDescription = "Filter",
                            tint = ViareSubtle,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = place.title,
                            fontFamily = FontFamily.Serif,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = (-0.4).sp,
                            color = ViareInk
                        )
                        Text(
                            text = "${place.locality} · ${place.category}",
                            fontSize = 9.5.sp,
                            color = Color(0xFF77827B),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                1.dp,
                                if (isSaved) Color(0xFFCBD9CE) else Color(0xFFEBEAE2),
                                RoundedCornerShape(10.dp)
                            )
                            .background(if (isSaved) Color(0xFFE7EEE8) else Color.White)
                            .clickable { onSaveToggle() }
                            .testTag("panel_save_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Filled.Check else Icons.Filled.BookmarkBorder,
                            contentDescription = if (isSaved) "Saved" else "Save place",
                            tint = if (isSaved) ViareGreen else Color(0xFF627068),
                            modifier = Modifier.size(17.dp)
                        )
                    }
                }

                // Rating line
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 9.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "4.7",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ViareInk
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
                        repeat(5) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = ViareGold,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }

                    Text(
                        text = place.reviewCount,
                        fontSize = 9.5.sp,
                        color = Color(0xFF829087)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .background(Color(0xFF527D60), CircleShape)
                        )
                        Text(
                            text = "Open now",
                            fontSize = 9.sp,
                            color = Color(0xFF527D60),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Photo Strip
                PhotoStrip(modifier = Modifier.padding(top = 11.dp))

                // Place description
                Text(
                    text = place.description,
                    fontSize = 10.sp,
                    lineHeight = 15.sp,
                    color = Color(0xFF68756D),
                    modifier = Modifier.padding(top = 9.dp)
                )
            }

            // Getting There Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                HorizontalDivider(color = ViareLine, thickness = 1.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "GETTING THERE",
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = ViareSubtleLight
                    )

                    Row(
                        modifier = Modifier
                            .clickable { onDirectionsClick() }
                            .testTag("directions_link"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = "Directions",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ViareGreen
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowOutward,
                            contentDescription = null,
                            tint = ViareGreen,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }

                // Mode Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    ViareModes.forEach { mode ->
                        val isSelected = mode.name == selectedMode
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) Color(0xFFDCE6DD) else Color.Transparent,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(if (isSelected) Color(0xFFEEF3ED) else Color.Transparent)
                                .clickable { onModeSelect(mode.name) }
                                .padding(vertical = 7.dp, horizontal = 2.dp)
                                .testTag("mode_tab_${mode.name}"),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = mode.icon,
                                contentDescription = mode.name,
                                tint = if (isSelected) ViareGreen else Color(0xFF839087),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = mode.name,
                                fontSize = 8.5.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) ViareGreen else Color(0xFF839087)
                            )
                        }
                    }
                }

                // Route Insight Card
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 11.dp)
                        .clip(RoundedCornerShape(9.dp))
                        .border(1.dp, Color(0xFFE7EDE6), RoundedCornerShape(9.dp))
                        .background(Color(0xFFF3F6F1))
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFE0E9DD)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AutoAwesome,
                            contentDescription = null,
                            tint = Color(0xFF51775F),
                            modifier = Modifier.size(13.dp)
                        )
                    }

                    Text(
                        text = place.routeInsight,
                        fontSize = 9.5.sp,
                        lineHeight = 14.sp,
                        color = Color(0xFF5E7064)
                    )
                }

                // Route Meta Line
                val currentModeTime = ViareModes.find { it.name == selectedMode }?.time ?: "36 min"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Schedule,
                            contentDescription = null,
                            tint = ViareInk,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = currentModeTime,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ViareInk
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = place.distance,
                            fontSize = 9.5.sp,
                            color = Color(0xFF78847C)
                        )
                        Box(
                            modifier = Modifier
                                .size(3.dp)
                                .background(Color(0xFFA6AEA8), CircleShape)
                        )
                        Text(
                            text = place.viaRoad,
                            fontSize = 9.5.sp,
                            color = Color(0xFF78847C)
                        )
                    }
                }

                // Start Navigation Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(43.dp)
                        .clip(RoundedCornerShape(11.dp))
                        .background(if (isNavigating) Color(0xFF66806C) else ViareGreen)
                        .clickable { onNavigateToggle() }
                        .padding(horizontal = 14.dp)
                        .testTag("navigate_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = if (isNavigating) Icons.Filled.Check else Icons.Filled.NearMe,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(17.dp)
                            )
                            Text(
                                text = if (isNavigating) "Navigation started" else "Start navigation",
                                color = Color.White,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Icon(
                            imageVector = Icons.Filled.ArrowOutward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }

            // Footer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
                    .border(width = 1.dp, color = ViareLine)
                    .padding(top = 11.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "viare maps",
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF728078)
                    )
                    Text(
                        text = "© OpenStreetMap",
                        fontSize = 9.sp,
                        color = Color(0xFF9AA29C)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clickable { onShareClick() }
                        .testTag("footer_share_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share",
                        tint = Color(0xFF859087),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
