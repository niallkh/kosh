package kosh.ui.component

import androidx.compose.runtime.Composable

inline fun <T : @Composable () -> Unit> nullUnless(
    condition: Boolean,
    content: T,
): T? = if (condition) content else null
