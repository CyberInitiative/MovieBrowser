package com.miroslav.levdikov.moviebrowser.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    onBackground = GhostWhite,
    background = IndigoInk50PerDarker,
    surface = IndigoInk,
    outline = GrayX11,
    onSurface = GhostWhite,
    onSurfaceVariant = GrayX11,
    errorContainer = Crimson,
    surfaceVariant = GrayX11
)

@Composable
fun MovieBrowserTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}