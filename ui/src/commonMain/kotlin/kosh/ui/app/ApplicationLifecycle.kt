package kosh.ui.app

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.arkivanov.essenty.lifecycle.Lifecycle

class ApplicationLifecycle(lifecycle: Lifecycle) : Lifecycle by lifecycle

val LocalApplicationLifecycle: ProvidableCompositionLocal<ApplicationLifecycle> =
    staticCompositionLocalOf { error("ApplicationLifecycle not provided") }
