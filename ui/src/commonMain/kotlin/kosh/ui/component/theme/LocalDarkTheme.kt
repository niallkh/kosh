package kosh.ui.component.theme

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

val LocalIsDark: ProvidableCompositionLocal<Boolean> =
    compositionLocalOf { false }

