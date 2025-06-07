package com.yu.pl.app.challeng.notemark.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private val ColorScheme = lightColorScheme(
    primary = Color(0xFF5977F7),
    onPrimary = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1B1B1C),
    surface = Color(0xFFEFEFF2),
    onSurfaceVariant = Color(0xFF535365),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    error = Color(0xFFE1294B),
)

val ColorScheme.BgGradient: Brush
    get() = Brush.linearGradient(
        0f to Color(0xFF58A1F8),
        1f to Color(0xFF5A4CF7),
        start = Offset.Zero,
        end = Offset.Infinite.copy(x = 0f)
    )


@Composable
fun NoteMarkTheme(
    content: @Composable () -> Unit,
) {

    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}