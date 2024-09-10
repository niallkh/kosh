package kosh.ui.component.modifier

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier

fun Modifier.optionalClickable(
    onClick: (() -> Unit)?,
): Modifier = if (onClick != null) {
    clickable(onClick = onClick)
} else {
    then(Modifier)
}

fun Modifier.optionalClickable(
    clickable: Boolean = true,
    onClick: () -> Unit,
): Modifier = if (clickable) {
    clickable(onClick = onClick)
} else {
    then(Modifier)
}
