package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ViareGreen
import com.example.ui.theme.ViareGreenRoute
import com.example.ui.theme.ViareMapBg
import com.example.ui.theme.ViareRoadMajor
import com.example.ui.theme.ViareRoadMinor
import com.example.ui.theme.ViareRoadShadow
import com.example.ui.theme.ViareSubtle
import com.example.ui.theme.ViareWater
import kotlin.math.roundToInt

@Composable
fun ViareMapCanvas(
    modifier: Modifier = Modifier,
    onSpotClick: (String) -> Unit,
    onPoiClick: (String) -> Unit
) {
    // Pan & zoom state for interactive map feel
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    // Pulsing animation for main destination beacon
    val infiniteTransition = rememberInfiniteTransition(label = "beacon_pulse")
    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_radius"
    )

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(ViareMapBg)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(0.85f, 2.2f)
                    offsetX = (offsetX + pan.x).coerceIn(-400f * scale, 400f * scale)
                    offsetY = (offsetY + pan.y).coerceIn(-400f * scale, 400f * scale)
                }
            }
            .testTag("map_canvas_container")
    ) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()

        // Coordinate scaling factors based on 1000x800 viewBox
        val sx = width / 1000f * scale
        val sy = height / 800f * scale
        val ox = offsetX
        val oy = offsetY

        // Draw Map Terrain, Roads, Water & Route Lines
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Background texture & terrain patches
            drawRect(color = ViareMapBg)

            // Terrain green zones
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFDCE7D4), Color.Transparent),
                    center = Offset(280f * sx + ox, 224f * sy + oy),
                    radius = 240f * sx
                ),
                center = Offset(280f * sx + ox, 224f * sy + oy),
                radius = 240f * sx
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFD6E2CF), Color.Transparent),
                    center = Offset(840f * sx + ox, 504f * sy + oy),
                    radius = 260f * sx
                ),
                center = Offset(840f * sx + ox, 504f * sy + oy),
                radius = 260f * sx
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFE2E9D9), Color.Transparent),
                    center = Offset(510f * sx + ox, 720f * sy + oy),
                    radius = 280f * sx
                ),
                center = Offset(510f * sx + ox, 720f * sy + oy),
                radius = 280f * sx
            )

            // Subtle road grid diagonals
            val step = 42.dp.toPx()
            var x = -size.height
            while (x < size.width + size.height) {
                drawLine(
                    color = Color(0x18FFFFFF),
                    start = Offset(x + ox * 0.3f, 0f),
                    end = Offset(x + size.height + ox * 0.3f, size.height),
                    strokeWidth = 1.dp.toPx()
                )
                x += step
            }

            // Function to map viewBox coords (1000x800) to Canvas coords
            fun vx(x: Float) = x * sx + ox
            fun vy(y: Float) = y * sy + oy

            // 1. Minor Roads (Cream thin roads)
            val minorRoad1 = Path().apply {
                moveTo(vx(-80f), vy(148f))
                cubicTo(vx(120f), vy(183f), vx(178f), vy(86f), vx(347f), vy(119f))
                cubicTo(vx(516f), vy(152f), vx(602f), vy(196f), vx(775f), vy(117f))
                cubicTo(vx(948f), vy(38f), vx(1019f), vy(134f), vx(1100f), vy(95f))
            }
            drawPath(
                path = minorRoad1,
                color = ViareRoadMinor.copy(alpha = 0.85f),
                style = Stroke(width = 6.dp.toPx() * scale, cap = StrokeCap.Round)
            )

            val minorRoad2 = Path().apply {
                moveTo(vx(-56f), vy(630f))
                cubicTo(vx(110f), vy(584f), vx(158f), vy(696f), vx(314f), vy(658f))
                cubicTo(vx(470f), vy(620f), vx(565f), vy(528f), vx(701f), vy(589f))
                cubicTo(vx(837f), vy(650f), vx(923f), vy(691f), vx(1090f), vy(612f))
            }
            drawPath(
                path = minorRoad2,
                color = ViareRoadMinor.copy(alpha = 0.85f),
                style = Stroke(width = 6.dp.toPx() * scale, cap = StrokeCap.Round)
            )

            val minorRoad3 = Path().apply {
                moveTo(vx(232f), vy(-45f))
                cubicTo(vx(198f), vy(112f), vx(279f), vy(209f), vx(231f), vy(332f))
                cubicTo(vx(183f), vy(455f), vx(168f), vy(570f), vx(245f), vy(863f))
            }
            drawPath(
                path = minorRoad3,
                color = ViareRoadMinor.copy(alpha = 0.85f),
                style = Stroke(width = 6.dp.toPx() * scale, cap = StrokeCap.Round)
            )

            val minorRoad4 = Path().apply {
                moveTo(vx(774f), vy(-53f))
                cubicTo(vx(721f), vy(101f), vx(813f), vy(225f), vx(771f), vy(365f))
                cubicTo(vx(729f), vy(505f), vx(752f), vy(601f), vx(868f), vy(849f))
            }
            drawPath(
                path = minorRoad4,
                color = ViareRoadMinor.copy(alpha = 0.85f),
                style = Stroke(width = 6.dp.toPx() * scale, cap = StrokeCap.Round)
            )

            // 2. Main Arterial Highway (Mathura–Govardhan Road)
            val mainRoad = Path().apply {
                moveTo(vx(-50f), vy(505f))
                cubicTo(vx(168f), vy(476f), vx(262f), vy(398f), vx(399f), vy(384f))
                cubicTo(vx(536f), vy(370f), vx(638f), vy(344f), vx(745f), vy(288f))
                cubicTo(vx(852f), vy(232f), vx(929f), vy(192f), vx(1075f), vy(210f))
            }
            // Road Shadow
            drawPath(
                path = mainRoad,
                color = ViareRoadShadow.copy(alpha = 0.52f),
                style = Stroke(width = 16.dp.toPx() * scale, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
            // Road Bed
            drawPath(
                path = mainRoad,
                color = ViareRoadMajor,
                style = Stroke(width = 11.dp.toPx() * scale, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )

            // 3. Water Canal Lines
            val waterLine1 = Path().apply {
                moveTo(vx(645f), vy(452f))
                cubicTo(vx(711f), vy(422f), vx(735f), vy(460f), vx(798f), vy(432f))
                cubicTo(vx(861f), vy(404f), vx(910f), vy(390f), vx(1002f), vy(430f))
            }
            val waterLine2 = Path().apply {
                moveTo(vx(651f), vy(472f))
                cubicTo(vx(713f), vy(441f), vx(740f), vy(481f), vx(802f), vy(453f))
                cubicTo(vx(864f), vy(425f), vx(912f), vy(410f), vx(1001f), vy(450f))
            }
            drawPath(
                path = waterLine1,
                color = ViareWater.copy(alpha = 0.9f),
                style = Stroke(width = 9.dp.toPx() * scale, cap = StrokeCap.Round)
            )
            drawPath(
                path = waterLine2,
                color = ViareWater.copy(alpha = 0.9f),
                style = Stroke(width = 9.dp.toPx() * scale, cap = StrokeCap.Round)
            )

            // 4. Active Smart Navigation Route
            val route = Path().apply {
                moveTo(vx(463f), vy(730f))
                cubicTo(vx(470f), vy(650f), vx(437f), vy(588f), vx(463f), vy(514f))
                cubicTo(vx(489f), vy(440f), vx(504f), vy(415f), vx(478f), vy(362f))
                cubicTo(vx(452f), vy(309f), vx(479f), vy(286f), vx(546f), vy(234f))
                cubicTo(vx(613f), vy(182f), vx(589f), vy(171f), vx(571f), vy(97f))
            }
            // Route Shadow/Halo
            drawPath(
                path = route,
                color = Color.White,
                style = Stroke(width = 10.dp.toPx() * scale, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
            // Route Active Forest Line
            drawPath(
                path = route,
                color = ViareGreenRoute,
                style = Stroke(
                    width = 4.5.dp.toPx() * scale,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }

        // Overlay: Map Labels (Interactive & positioned accurately)
        // 1. GOVARDHAN Label
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        (width * 0.22f * scale + offsetX).roundToInt(),
                        (height * 0.21f * scale + offsetY).roundToInt()
                    )
                }
                .clickable { onSpotClick("Govardhan") }
        ) {
            Text(
                text = "GOVARDHAN",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                color = Color(0xFF7C8979)
            )
        }

        // 2. Chhatikara Label
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        (width * 0.72f * scale + offsetX).roundToInt(),
                        (height * 0.14f * scale + offsetY).roundToInt()
                    )
                }
                .clickable { onSpotClick("Chhatikara") }
        ) {
            Text(
                text = "Chhatikara",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF7C8979)
            )
        }

        // 3. Radhakund Label
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        (width * 0.18f * scale + offsetX).roundToInt(),
                        (height * 0.70f * scale + offsetY).roundToInt()
                    )
                }
                .clickable { onSpotClick("Radhakund") }
        ) {
            Text(
                text = "Radhakund",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF7C8979)
            )
        }

        // 4. Deeg Road Label
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        (width * 0.75f * scale + offsetX).roundToInt(),
                        (height * 0.75f * scale + offsetY).roundToInt()
                    )
                }
        ) {
            Text(
                text = "Deeg Road",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF7C8979)
            )
        }

        // 5. Braj café POI Pill
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        (width * 0.70f * scale + offsetX).roundToInt(),
                        (height * 0.28f * scale + offsetY).roundToInt()
                    )
                }
                .shadow(4.dp, RoundedCornerShape(9.dp), spotColor = Color(0x1A35412C))
                .clip(RoundedCornerShape(9.dp))
                .border(1.dp, Color(0xFFE6E7DA), RoundedCornerShape(9.dp))
                .background(Color(0xFFFFFEFA))
                .clickable { onPoiClick("Braj café") }
                .padding(horizontal = 9.dp, vertical = 5.dp)
                .testTag("poi_braj_cafe")
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "☕ ", fontSize = 11.sp)
                Text(
                    text = "Braj café",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5F6F63)
                )
            }
        }

        // 6. IndianOil POI Pill
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        (width * 0.74f * scale + offsetX).roundToInt(),
                        (height * 0.65f * scale + offsetY).roundToInt()
                    )
                }
                .shadow(4.dp, RoundedCornerShape(9.dp), spotColor = Color(0x1A35412C))
                .clip(RoundedCornerShape(9.dp))
                .border(1.dp, Color(0xFFE6E7DA), RoundedCornerShape(9.dp))
                .background(Color(0xFFFFFEFA))
                .clickable { onPoiClick("IndianOil") }
                .padding(horizontal = 9.dp, vertical = 5.dp)
                .testTag("poi_indianoil")
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "⛽ ", fontSize = 11.sp)
                Text(
                    text = "IndianOil",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5F6F63)
                )
            }
        }

        // Spot One: Kusum Sarovar Destination Spot (Pulsing Green Beacon)
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        (width * 0.46f * scale + offsetX - 17.dp.toPx()).roundToInt(),
                        (height * 0.46f * scale + offsetY - 17.dp.toPx()).roundToInt()
                    )
                }
                .size(34.dp)
                .clickable { onSpotClick("Kusum Sarovar") }
                .testTag("map_spot_kusum_sarovar"),
            contentAlignment = Alignment.Center
        ) {
            // Pulse ring
            Box(
                modifier = Modifier
                    .size((34 * pulseRadius).dp)
                    .background(Color(0x33315C49), CircleShape)
            )
            // Core marker
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .shadow(6.dp, CircleShape, spotColor = Color(0x40233B2B))
                    .border(2.5.dp, Color.White, CircleShape)
                    .background(ViareGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(Color.White, CircleShape)
                )
            }
        }

        // Spot Two: Amber Marker (Radhakund area)
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        (width * 0.24f * scale + offsetX - 9.dp.toPx()).roundToInt(),
                        (height * 0.66f * scale + offsetY - 9.dp.toPx()).roundToInt()
                    )
                }
                .size(18.dp)
                .shadow(4.dp, CircleShape, spotColor = Color(0x38233B2B))
                .border(2.dp, Color.White, CircleShape)
                .background(Color(0xFFB37F4D), CircleShape)
                .clickable { onSpotClick("Radhakund Spot") }
                .testTag("map_spot_radhakund"),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(Color.White, CircleShape)
            )
        }

        // Spot Three: Sage Marker (Chhatikara junction)
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        (width * 0.75f * scale + offsetX - 9.dp.toPx()).roundToInt(),
                        (height * 0.27f * scale + offsetY - 9.dp.toPx()).roundToInt()
                    )
                }
                .size(18.dp)
                .shadow(4.dp, CircleShape, spotColor = Color(0x38233B2B))
                .border(2.dp, Color.White, CircleShape)
                .background(Color(0xFF748F69), CircleShape)
                .clickable { onSpotClick("Chhatikara Junction") }
                .testTag("map_spot_chhatikara"),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(Color.White, CircleShape)
            )
        }
    }
}
