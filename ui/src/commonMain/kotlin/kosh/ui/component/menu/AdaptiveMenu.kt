package kosh.ui.component.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import kosh.ui.component.bottomsheet.DismissCallback
import kosh.ui.component.bottomsheet.KoshModalBottomSheet
import kosh.ui.component.single.single

@Composable
fun AdaptiveMenu(
    visible: Boolean,
    onDismiss: () -> Unit,
    content: @Composable AdaptiveMenuScope.(DismissCallback) -> Unit,
) {
    if (true) {
        if (visible) {
            KoshModalBottomSheet(
                onDismissRequest = onDismiss,
                dragHandle = null,
                shape = RectangleShape,
            ) {
                val callback = rememberUpdatedState(it)

                val dismissCallback = DismissCallback { onFinish ->
                    callback.value {
                        onDismiss()
                        onFinish()
                    }
                }

                Column(Modifier.padding(vertical = 8.dp)) {
                    MobileAdaptiveMenuScope.content(dismissCallback)
                }
            }
        }
    } else {
        DropdownMenu(
            expanded = visible,
            onDismissRequest = onDismiss,
        ) {
            DesktopAdaptiveMenuScope.content(remember {
                DismissCallback { onFinish ->
                    onDismiss()
                    onFinish()
                }
            })
        }
    }
}

@Composable
fun AdaptiveMoreMenu(
    iconButton: (@Composable (() -> Unit) -> Unit)? = null,
    content: @Composable AdaptiveMenuScope.(DismissCallback) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    if (iconButton != null) {
        iconButton { expanded = true }
    } else {
        MoreButton { expanded = true }
    }

    AdaptiveMenu(
        visible = expanded,
        onDismiss = { expanded = false },
        content = content
    )
}

@Composable
fun MoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(modifier = modifier, onClick = onClick.single()) {
        Icon(Icons.Default.MoreVert, contentDescription = "More")
    }
}

interface AdaptiveMenuScope {
    val menu: Boolean
}

private object MobileAdaptiveMenuScope : AdaptiveMenuScope {
    override val menu: Boolean
        get() = false
}

private object DesktopAdaptiveMenuScope : AdaptiveMenuScope {
    override val menu: Boolean
        get() = false
}

@Composable
fun AdaptiveMenuScope.AdaptiveMenuItem(
    onClick: () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit,
) {
    if (menu) {
        DropdownMenuItem(
            modifier = modifier,
            onClick = onClick,
            text = text,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
        )
    } else {
        SheetMenuItem(
            modifier = modifier,
            onClick = onClick,
            text = text,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
        )
    }
}

@Composable
private fun SheetMenuItem(
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit,
    onClick: () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (leadingIcon != null) {
                CompositionLocalProvider(
                    LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant
                ) {
                    leadingIcon()
                    Spacer(Modifier.width(12.dp))
                }
            }

            Box(Modifier.weight(1f)) {
                CompositionLocalProvider(
                    LocalTextStyle provides MaterialTheme.typography.labelLarge
                ) {
                    text()
                }
            }

            if (trailingIcon != null) {
                CompositionLocalProvider(
                    LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant
                ) {
                    Spacer(Modifier.width(12.dp))
                    trailingIcon()
                }
            }
        }
    }
}
