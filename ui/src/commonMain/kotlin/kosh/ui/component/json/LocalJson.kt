package kosh.ui.component.json

import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.serialization.json.Json

val LocalJson = staticCompositionLocalOf<Json> {
    error("LocalJson not provided")
}
