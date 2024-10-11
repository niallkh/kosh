package kosh.app

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kosh.app.theme.appTypography
import kosh.app.theme.darkScheme
import kosh.app.theme.lightScheme

@Composable
internal actual fun KoshTheme(content: @Composable () -> Unit) {
    val darkTheme: Boolean = isSystemInDarkTheme()

    val colorScheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(LocalContext.current)
            else dynamicLightColorScheme(LocalContext.current)
        }

        darkTheme -> darkScheme()
        else -> lightScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = appTypography(),
    ) {
        content()
    }
}
