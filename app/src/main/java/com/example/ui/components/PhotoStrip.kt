package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ViareSandstone
import com.example.ui.theme.ViareSandstoneDark
import com.example.ui.theme.ViareSandstoneLight

@Composable
fun PhotoStrip(
    modifier: Modifier = Modifier,
    onPhotoClick: ((String) -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Photo 1: Quiet Ghats with large Chhatri arch
            Box(
                modifier = Modifier
                    .weight(1.15f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Multi-stop gradient
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFB8C6A1),
                                Color(0xFF6E927A),
                                Color(0xFFC8B28B),
                                Color(0xFF8C755E),
                                Color(0xFF526E62)
                            ),
                            start = Offset.Zero,
                            end = Offset(size.width, size.height)
                        )
                    )
                    // Architectural Chhatri Arch
                    val archWidth = size.width * 0.72f
                    val archHeight = size.height * 0.62f
                    val left = (size.width - archWidth) / 2f
                    val top = size.height * 0.18f

                    val path = Path().apply {
                        moveTo(left, top + archHeight)
                        lineTo(left, top + archWidth / 2f)
                        arcTo(
                            rect = androidx.compose.ui.geometry.Rect(
                                left,
                                top,
                                left + archWidth,
                                top + archWidth
                            ),
                            startAngleDegrees = 180f,
                            sweepAngleDegrees = 180f,
                            forceMoveTo = false
                        )
                        lineTo(left + archWidth, top + archHeight)
                    }
                    drawPath(
                        path = path,
                        color = ViareSandstoneDark,
                        style = Stroke(width = 16f, cap = StrokeCap.Round)
                    )
                    drawPath(
                        path = path,
                        color = ViareSandstone,
                        style = Stroke(width = 9f, cap = StrokeCap.Round)
                    )
                    // Bottom vignette
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xBF16221B)),
                            startY = size.height * 0.35f,
                            endY = size.height
                        )
                    )
                }
                Text(
                    text = "THE QUIET GHATS",
                    color = Color.White,
                    fontSize = 7.5.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.7.sp,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 7.dp, vertical = 7.dp)
                )
            }

            // Photo 2: Sandstone Chhatris (Dual Arches)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFD6C29B),
                                Color(0xFF9B8460),
                                Color(0xFF607A69)
                            ),
                            start = Offset.Zero,
                            end = Offset(size.width, size.height)
                        )
                    )
                    // First Arch
                    val aW = size.width * 0.44f
                    val aH = size.height * 0.58f
                    val pathA = Path().apply {
                        moveTo(size.width * 0.15f, size.height * 0.18f + aH)
                        lineTo(size.width * 0.15f, size.height * 0.18f + aW / 2f)
                        arcTo(
                            rect = androidx.compose.ui.geometry.Rect(
                                size.width * 0.15f,
                                size.height * 0.18f,
                                size.width * 0.15f + aW,
                                size.height * 0.18f + aW
                            ),
                            startAngleDegrees = 180f,
                            sweepAngleDegrees = 180f,
                            forceMoveTo = false
                        )
                        lineTo(size.width * 0.15f + aW, size.height * 0.18f + aH)
                    }
                    drawPath(
                        path = pathA,
                        color = ViareSandstoneLight,
                        style = Stroke(width = 8f, cap = StrokeCap.Round)
                    )

                    // Second Arch (Offset)
                    val pathB = Path().apply {
                        moveTo(size.width * 0.58f, size.height * 0.22f + aH * 0.85f)
                        lineTo(size.width * 0.58f, size.height * 0.22f + (aW * 0.85f) / 2f)
                        arcTo(
                            rect = androidx.compose.ui.geometry.Rect(
                                size.width * 0.58f,
                                size.height * 0.22f,
                                size.width * 0.58f + aW * 0.85f,
                                size.height * 0.22f + aW * 0.85f
                            ),
                            startAngleDegrees = 180f,
                            sweepAngleDegrees = 180f,
                            forceMoveTo = false
                        )
                        lineTo(size.width * 0.58f + aW * 0.85f, size.height * 0.22f + aH * 0.85f)
                    }
                    drawPath(
                        path = pathB,
                        color = Color(0xFFA18B69),
                        style = Stroke(width = 6f, cap = StrokeCap.Round)
                    )

                    // Bottom vignette
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xBF16221B)),
                            startY = size.height * 0.35f,
                            endY = size.height
                        )
                    )
                }
                Text(
                    text = "SANDSTONE CHHATRIS",
                    color = Color.White,
                    fontSize = 7.5.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.7.sp,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 7.dp, vertical = 7.dp)
                )
            }

            // Photo 3: Still Water (Calm pond reflection)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFA8B99D),
                                Color(0xFF718E7E),
                                Color(0xFF526F6A)
                            ),
                            start = Offset.Zero,
                            end = Offset(size.width, size.height)
                        )
                    )
                    // Water ripples & reflection oval
                    drawOval(
                        color = Color(0x9ABFD0B7),
                        topLeft = Offset(-size.width * 0.1f, size.height * 0.52f),
                        size = Size(size.width * 1.2f, size.height * 0.22f)
                    )
                    drawOval(
                        color = Color(0x60BFD0B7),
                        topLeft = Offset(-size.width * 0.05f, size.height * 0.65f),
                        size = Size(size.width * 1.1f, size.height * 0.18f)
                    )
                    // Bottom vignette
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xBF16221B)),
                            startY = size.height * 0.35f,
                            endY = size.height
                        )
                    )
                }
                Text(
                    text = "STILL WATER",
                    color = Color.White,
                    fontSize = 7.5.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.7.sp,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 7.dp, vertical = 7.dp)
                )
            }
        }

        // Photo Count Badge
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(6.dp)
                .background(Color(0xB324352D), RoundedCornerShape(5.dp))
                .padding(horizontal = 6.dp, vertical = 3.dp)
        ) {
            Text(
                text = "24 photos",
                color = Color.White,
                fontSize = 8.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
