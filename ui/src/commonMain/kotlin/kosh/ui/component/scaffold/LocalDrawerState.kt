package kosh.ui.component.scaffold

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

val LocalDrawerState: ProvidableCompositionLocal<DrawerState> =
    compositionLocalOf { DrawerState(DrawerValue.Closed) }

val currentDrawerState: DrawerState
    @Composable inline get() = LocalDrawerState.current
