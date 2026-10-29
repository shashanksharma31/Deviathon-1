package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = ViareGreen,
    onPrimary = Color.White,
    primaryContainer = ViareGreenSoft,
    onPrimaryContainer = ViareGreenDark,
    secondary = ViareGreenSoft,
    onSecondary = ViareGreen,
    tertiary = ViareAmber,
    onTertiary = Color.White,
    background = ViareBackground,
    onBackground = ViareInk,
    surface = ViareCardBg,
    onSurface = ViareInk,
    surfaceVariant = ViarePanelBg,
    onSurfaceVariant = ViareSubtle,
    outline = ViareLine,
    outlineVariant = ViareBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF62A27B),
    onPrimary = Color(0xFF14241B),
    primaryContainer = Color(0xFF223C2F),
    onPrimaryContainer = Color(0xFFBCE1CB),
    secondary = Color(0xFF2B3A31),
    onSecondary = Color(0xFFC7D7CD),
    tertiary = ViareAmber,
    onTertiary = Color.White,
    background = Color(0xFF161F1A),
    onBackground = Color(0xFFE5EDE7),
    surface = Color(0xFF1C2721),
    onSurface = Color(0xFFE5EDE7),
    surfaceVariant = Color(0xFF202C25),
    onSurfaceVariant = Color(0xFFA5B2A9),
    outline = Color(0xFF2F3E34),
    outlineVariant = Color(0xFF28362D)
)

@Composable
fun ViareTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
