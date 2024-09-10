package kosh.ui.component.button

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import kosh.ui.component.single.single

@Composable
fun LoadingTextButton(
    text: () -> Unit,
    loading: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ButtonDefaults.buttonColors()

    TextButton(
        modifier = modifier.defaultMinSize(minWidth = 96.dp),
        onClick = onClick.single(),
        enabled = enabled && loading.not(),
        colors = remember(loading, colors) {
            if (loading) {
                colors.copy(
                    disabledContainerColor = colors.containerColor,
                    disabledContentColor = colors.contentColor,
                )
            } else {
                colors
            }
        }
    ) {
        Row(
            modifier = Modifier.animateContentSize(),
            horizontalArrangement = Arrangement.Center,
        ) {
            text()

            if (loading) {
                Spacer(Modifier.width(8.dp))

                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeCap = StrokeCap.Round,
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp,
                )
            }
        }
    }
}
