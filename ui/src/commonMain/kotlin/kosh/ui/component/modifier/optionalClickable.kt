package kosh.ui.component.modifier

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier

inline fun Modifier.optionalClickable(
    clickable: Boolean = true,
    noinline onClick: (() -> Unit)?,
): Modifier = if (onClick != null && clickable) {
    clickable(onClick = onClick)
} else {
    then(Modifier)
}
