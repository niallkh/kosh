package kosh.app.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
internal expect fun dynamicColorScheme(dark: Boolean): ColorScheme?
