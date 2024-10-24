package kosh.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import kosh.app.theme.appTypography
import kosh.app.theme.darkScheme
import kosh.app.theme.dynamicColorScheme
import kosh.app.theme.lightScheme

@Composable
internal fun KoshTheme(content: @Composable () -> Unit) {
    val darkTheme: Boolean = isSystemInDarkTheme()

    val colorScheme = when (val dynamic = dynamicColorScheme(darkTheme)) {
        null -> if (darkTheme) darkScheme() else lightScheme()
        else -> dynamic
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = appTypography(),
    ) {
        content()
    }
}
