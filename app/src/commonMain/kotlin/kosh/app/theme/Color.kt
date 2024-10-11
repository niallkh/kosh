package kosh.app.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

private val primaryLight = Color(0xFF445E91)
private val onPrimaryLight = Color(0xFFFFFFFF)
private val primaryContainerLight = Color(0xFFD8E2FF)
private val onPrimaryContainerLight = Color(0xFF001A42)
private val secondaryLight = Color(0xFF575E71)
private val onSecondaryLight = Color(0xFFFFFFFF)
private val secondaryContainerLight = Color(0xFFDBE2F9)
private val onSecondaryContainerLight = Color(0xFF141B2C)
private val tertiaryLight = Color(0xFF006876)
private val onTertiaryLight = Color(0xFFFFFFFF)
private val tertiaryContainerLight = Color(0xFFA1EFFF)
private val onTertiaryContainerLight = Color(0xFF001F25)
private val errorLight = Color(0xFFBA1A1A)
private val onErrorLight = Color(0xFFFFFFFF)
private val errorContainerLight = Color(0xFFFFDAD6)
private val onErrorContainerLight = Color(0xFF410002)
private val backgroundLight = Color(0xFFF9F9FF)
private val onBackgroundLight = Color(0xFF1A1B20)
private val surfaceLight = Color(0xFFF9F9FF)
private val onSurfaceLight = Color(0xFF1A1B20)
private val surfaceVariantLight = Color(0xFFE1E2EC)
private val onSurfaceVariantLight = Color(0xFF44474F)
private val outlineLight = Color(0xFF75777F)
private val outlineVariantLight = Color(0xFFC4C6D0)
private val scrimLight = Color(0xFF000000)
private val inverseSurfaceLight = Color(0xFF2F3036)
private val inverseOnSurfaceLight = Color(0xFFF0F0F7)
private val inversePrimaryLight = Color(0xFFADC6FF)
private val surfaceDimLight = Color(0xFFD9D9E0)
private val surfaceBrightLight = Color(0xFFF9F9FF)
private val surfaceContainerLowestLight = Color(0xFFFFFFFF)
private val surfaceContainerLowLight = Color(0xFFF3F3FA)
private val surfaceContainerLight = Color(0xFFEEEDF4)
private val surfaceContainerHighLight = Color(0xFFE8E7EF)
private val surfaceContainerHighestLight = Color(0xFFE2E2E9)

private val primaryDark = Color(0xFFADC6FF)
private val onPrimaryDark = Color(0xFF112F60)
private val primaryContainerDark = Color(0xFF2B4678)
private val onPrimaryContainerDark = Color(0xFFD8E2FF)
private val secondaryDark = Color(0xFFBFC6DC)
private val onSecondaryDark = Color(0xFF293041)
private val secondaryContainerDark = Color(0xFF3F4759)
private val onSecondaryContainerDark = Color(0xFFDBE2F9)
private val tertiaryDark = Color(0xFF83D3E3)
private val onTertiaryDark = Color(0xFF00363E)
private val tertiaryContainerDark = Color(0xFF004E59)
private val onTertiaryContainerDark = Color(0xFFA1EFFF)
private val errorDark = Color(0xFFFFB4AB)
private val onErrorDark = Color(0xFF690005)
private val errorContainerDark = Color(0xFF93000A)
private val onErrorContainerDark = Color(0xFFFFDAD6)
private val backgroundDark = Color(0xFF111318)
private val onBackgroundDark = Color(0xFFE2E2E9)
private val surfaceDark = Color(0xFF111318)
private val onSurfaceDark = Color(0xFFE2E2E9)
private val surfaceVariantDark = Color(0xFF44474F)
private val onSurfaceVariantDark = Color(0xFFC4C6D0)
private val outlineDark = Color(0xFF8E9099)
private val outlineVariantDark = Color(0xFF44474F)
private val scrimDark = Color(0xFF000000)
private val inverseSurfaceDark = Color(0xFFE2E2E9)
private val inverseOnSurfaceDark = Color(0xFF2F3036)
private val inversePrimaryDark = Color(0xFF445E91)
private val surfaceDimDark = Color(0xFF111318)
private val surfaceBrightDark = Color(0xFF37393E)
private val surfaceContainerLowestDark = Color(0xFF0C0E13)
private val surfaceContainerLowDark = Color(0xFF1A1B20)
private val surfaceContainerDark = Color(0xFF1E1F25)
private val surfaceContainerHighDark = Color(0xFF282A2F)
private val surfaceContainerHighestDark = Color(0xFF33353A)

@Composable
internal fun lightScheme() = remember {
    lightColorScheme(
        primary = primaryLight,
        onPrimary = onPrimaryLight,
        primaryContainer = primaryContainerLight,
        onPrimaryContainer = onPrimaryContainerLight,
        secondary = secondaryLight,
        onSecondary = onSecondaryLight,
        secondaryContainer = secondaryContainerLight,
        onSecondaryContainer = onSecondaryContainerLight,
        tertiary = tertiaryLight,
        onTertiary = onTertiaryLight,
        tertiaryContainer = tertiaryContainerLight,
        onTertiaryContainer = onTertiaryContainerLight,
        error = errorLight,
        onError = onErrorLight,
        errorContainer = errorContainerLight,
        onErrorContainer = onErrorContainerLight,
        background = backgroundLight,
        onBackground = onBackgroundLight,
        surface = surfaceLight,
        onSurface = onSurfaceLight,
        surfaceVariant = surfaceVariantLight,
        onSurfaceVariant = onSurfaceVariantLight,
        outline = outlineLight,
        outlineVariant = outlineVariantLight,
        scrim = scrimLight,
        inverseSurface = inverseSurfaceLight,
        inverseOnSurface = inverseOnSurfaceLight,
        inversePrimary = inversePrimaryLight,
        surfaceDim = surfaceDimLight,
        surfaceBright = surfaceBrightLight,
        surfaceContainerLowest = surfaceContainerLowestLight,
        surfaceContainerLow = surfaceContainerLowLight,
        surfaceContainer = surfaceContainerLight,
        surfaceContainerHigh = surfaceContainerHighLight,
        surfaceContainerHighest = surfaceContainerHighestLight,
    )
}

@Composable
internal fun darkScheme() = remember {
    darkColorScheme(
        primary = primaryDark,
        onPrimary = onPrimaryDark,
        primaryContainer = primaryContainerDark,
        onPrimaryContainer = onPrimaryContainerDark,
        secondary = secondaryDark,
        onSecondary = onSecondaryDark,
        secondaryContainer = secondaryContainerDark,
        onSecondaryContainer = onSecondaryContainerDark,
        tertiary = tertiaryDark,
        onTertiary = onTertiaryDark,
        tertiaryContainer = tertiaryContainerDark,
        onTertiaryContainer = onTertiaryContainerDark,
        error = errorDark,
        onError = onErrorDark,
        errorContainer = errorContainerDark,
        onErrorContainer = onErrorContainerDark,
        background = backgroundDark,
        onBackground = onBackgroundDark,
        surface = surfaceDark,
        onSurface = onSurfaceDark,
        surfaceVariant = surfaceVariantDark,
        onSurfaceVariant = onSurfaceVariantDark,
        outline = outlineDark,
        outlineVariant = outlineVariantDark,
        scrim = scrimDark,
        inverseSurface = inverseSurfaceDark,
        inverseOnSurface = inverseOnSurfaceDark,
        inversePrimary = inversePrimaryDark,
        surfaceDim = surfaceDimDark,
        surfaceBright = surfaceBrightDark,
        surfaceContainerLowest = surfaceContainerLowestDark,
        surfaceContainerLow = surfaceContainerLowDark,
        surfaceContainer = surfaceContainerDark,
        surfaceContainerHigh = surfaceContainerHighDark,
        surfaceContainerHighest = surfaceContainerHighestDark,
    )
}
