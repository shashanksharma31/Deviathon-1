package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PlaceInfo
import com.example.ui.theme.ViareCardBg
import com.example.ui.theme.ViareGreen
import com.example.ui.theme.ViareGreenSoft
import com.example.ui.theme.ViareInk
import com.example.ui.theme.ViareLine
import com.example.ui.theme.ViareLiveGreen
import com.example.ui.theme.ViareSandstone
import com.example.ui.theme.ViareSandstoneDark
import com.example.ui.theme.ViareSubtle
import com.example.ui.theme.ViareSun

@Composable
fun ArrivalCard(
    place: PlaceInfo,
    selectedMode: String,
    modeTime: String,
    isSaved: Boolean,
    onSaveToggle: () -> Unit,
    onUseModeClick: () -> Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(15.dp),
                ambientColor = Color(0x33273B2D),
                spotColor = Color(0x33273B2D)
            )
            .border(1.dp, Color(0xFFEBE9DE), RoundedCornerShape(15.dp))
            .clickable { onCardClick() }
            .testTag("arrival_card"),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = ViareCardBg)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Illustrated Arrival Header Art
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(118.dp)
                    .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Sky gradient
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFC5D0A9),
                                Color(0xFFA9BD9B),
                                Color(0xFF8EA68B),
                                Color(0xFF688575),
                                Color(0xFF507267)
                            ),
                            start = Offset.Zero,
                            end = Offset(size.width * 0.9f, size.height)
                        )
                    )

                    // Distant hill curve
                    val hillPath = Path().apply {
                        moveTo(-size.width * 0.1f, size.height)
                        cubicTo(
                            size.width * 0.1f, size.height * 0.35f,
                            size.width * 0.8f, size.height * 0.45f,
                            size.width * 1.15f, size.height
                        )
                        close()
                    }
                    drawPath(
                        path = hillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF91A98C), Color(0xFF526E62))
                        )
                    )

                    // Sun orb
                    drawCircle(
                        color = ViareSun.copy(alpha = 0.88f),
                        radius = 20.dp.toPx(),
                        center = Offset(size.width * 0.78f, size.height * 0.32f)
                    )

                    // Arch A (Main sandstone pavilion)
                    val aW = 76.dp.toPx()
                    val aH = 58.dp.toPx()
                    val aL = size.width * 0.28f
                    val aB = size.height * 0.76f

                    val pathArchA = Path().apply {
                        moveTo(aL, aB)
                        lineTo(aL, aB - aH + aW / 2f)
                        arcTo(
                            rect = Rect(aL, aB - aH, aL + aW, aB - aH + aW),
                            startAngleDegrees = 180f,
                            sweepAngleDegrees = 180f,
                            forceMoveTo = false
                        )
                        lineTo(aL + aW, aB)
                    }
                    drawPath(
                        path = pathArchA,
                        color = ViareSandstoneDark,
                        style = Stroke(width = 14f, cap = StrokeCap.Round)
                    )
                    drawPath(
                        path = pathArchA,
                        color = ViareSandstone,
                        style = Stroke(width = 8f, cap = StrokeCap.Round)
                    )

                    // Arch B (Secondary Chhatri arch)
                    val bW = 54.dp.toPx()
                    val bH = 46.dp.toPx()
                    val bL = aL + aW * 0.82f
                    val bB = size.height * 0.79f

                    val pathArchB = Path().apply {
                        moveTo(bL, bB)
                        lineTo(bL, bB - bH + bW / 2f)
                        arcTo(
                            rect = Rect(bL, bB - bH, bL + bW, bB - bH + bW),
                            startAngleDegrees = 180f,
                            sweepAngleDegrees = 180f,
                            forceMoveTo = false
                        )
                        lineTo(bL + bW, bB)
                    }
                    drawPath(
                        path = pathArchB,
                        color = ViareSandstone,
                        style = Stroke(width = 6f, cap = StrokeCap.Round)
                    )

                    // Water pool at base
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xCC819D8A), Color(0xF0617E75))
                        ),
                        topLeft = Offset(0f, size.height * 0.76f),
                        size = Size(size.width, size.height * 0.24f)
                    )
                }

                // Tags in image header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "GOVARDHAN · GARDEN",
                        color = Color.White,
                        fontSize = 7.5.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .background(ViareLiveGreen, CircleShape)
                        )
                        Text(
                            text = "LIGHT TRAFFIC",
                            color = Color.White,
                            fontSize = 7.5.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                    }
                }
            }

            // Card Content
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "INTELLIGENT NAVIGATION",
                        color = Color(0xFF829087),
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )

                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Color(0xFFEBEAE2), RoundedCornerShape(8.dp))
                            .background(if (isSaved) ViareGreenSoft else Color.White)
                            .clickable { onSaveToggle() }
                            .testTag("arrival_save_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Filled.Check else Icons.Filled.BookmarkBorder,
                            contentDescription = if (isSaved) "Saved" else "Save place",
                            tint = if (isSaved) ViareGreen else ViareSubtle,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Text(
                    text = place.title,
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = ViareInk,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Text(
                    text = "${place.category} · ${place.address}",
                    fontSize = 9.5.sp,
                    color = ViareSubtle,
                    modifier = Modifier.padding(top = 2.dp)
                )

                HorizontalDivider(
                    color = ViareLine,
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.TwoWheeler,
                            contentDescription = selectedMode,
                            tint = ViareSubtle,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "$selectedMode · $modeTime",
                            fontSize = 9.5.sp,
                            color = ViareSubtle,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFEAF0E9))
                            .clickable { onUseModeClick() }
                            .padding(horizontal = 9.dp, vertical = 6.dp)
                            .testTag("use_mode_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Use mode",
                                color = ViareGreen,
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Icon(
                                imageVector = Icons.Filled.ArrowOutward,
                                contentDescription = null,
                                tint = ViareGreen,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
