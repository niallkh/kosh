package kosh.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import kosh.ui.component.theme.LocalIsDark

@Composable
actual fun KoshTheme(content: @Composable () -> Unit) {
    val darkTheme: Boolean = isSystemInDarkTheme()

    val colorScheme = when {
        darkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
    ) {
        CompositionLocalProvider(
            LocalIsDark provides darkTheme
        ) {
            content()
        }
    }
}
