package kosh.app

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kosh.ui.resources.icons.KoshForeground

internal val lightBackgroundColor = Color(0xFF1C1C1E)
internal val darkBackgroundColor = Color(0xFFF2F2F7)

@Composable
internal fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .background(
                if (isSystemInDarkTheme()) lightBackgroundColor
                else darkBackgroundColor
            )

    ) {
        Icon(
            KoshForeground,
            modifier = Modifier
                .size(128.dp)
                .align(Alignment.Center),
            contentDescription = null,
            tint = Color.Unspecified,
        )
    }
}
