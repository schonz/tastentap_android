package com.tastentap.tastentap_android_app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF800020),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF95162D),
    onPrimaryContainer = Color(0xFFFFE2E2),
    secondary = Color(0xFFFADADD),
    onSecondary = Color(0xFF5A4447),
    background = Color(0xFFFDF8F8),
    onBackground = Color(0xFF1C1B1B),
    surface = Color(0xFFFDF8F8),
    onSurface = Color(0xFF1C1B1B)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFFFB3B5),
    onPrimary = Color(0xFF680018),
    primaryContainer = Color(0xFF70001B),
    onPrimaryContainer = Color(0xFFFFAFB2),
    secondary = Color(0xFFECCDD0),
    onSecondary = Color(0xFF4F3A3C),
    background = Color(0xFF141313),
    onBackground = Color(0xFFE5E2E1),
    surface = Color(0xFF141313),
    onSurface = Color(0xFFE5E2E1)
)

@Composable
fun TasteNTapTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        //typography = Typography, // Ensure you define this in Typography.kt
        //shapes = Shapes,         // Ensure you define this in Shape.kt
        content = content
    )
}