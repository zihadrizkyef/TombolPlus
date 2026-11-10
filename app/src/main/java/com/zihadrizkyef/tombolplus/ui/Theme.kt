package com.zihadrizkyef.tombolplus.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Bg = Color(0xFFFBFBF9)
val CardBg = Color(0xFFFFFFFF)
val Hair = Color(0xFFEEEDE8)
val SegTrack = Color(0xFFF2F1EC)
val Ink = Color(0xFF14171C)
val Muted = Color(0xFF6B727B)
val Faint = Color(0xFF8B929B)
val Amber = Color(0xFFC97D00)
val AmberBright = Color(0xFFFFB020)
val Danger = Color(0xFFC0342E)
val OkGreen = Color(0xFF15794E)
val OverlaySurface = Color(0xFF101318)
val OverlayInk = Color(0xFFEEF1F4)
val OverlayMuted = Color(0xFF98A1AD)

@Composable
fun TombolTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Amber,
            onPrimary = Color.White,
            background = Bg,
            onBackground = Ink,
            surface = CardBg,
            onSurface = Ink
        ),
        content = content
    )
}
