package kosh.app.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily

@Composable
internal fun appTypography(): Typography {
    return remember {
        val baseline = Typography()
        baseline.copy(
            displayLarge = baseline.displayLarge.copy(fontFamily = FontFamily.Monospace),
            displayMedium = baseline.displayMedium.copy(fontFamily = FontFamily.Monospace),
            displaySmall = baseline.displaySmall.copy(fontFamily = FontFamily.Monospace),
            headlineLarge = baseline.headlineLarge.copy(fontFamily = FontFamily.Monospace),
            headlineMedium = baseline.headlineMedium.copy(fontFamily = FontFamily.Monospace),
            headlineSmall = baseline.headlineSmall.copy(fontFamily = FontFamily.Monospace),
            titleLarge = baseline.titleLarge.copy(fontFamily = FontFamily.Monospace),
            titleMedium = baseline.titleMedium.copy(fontFamily = FontFamily.Monospace),
            titleSmall = baseline.titleSmall.copy(fontFamily = FontFamily.Monospace),
        )
    }
}

