package kosh.ui.component.illustration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun Illustration(
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    message: @Composable () -> Unit,
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Image(
            imageVector,
            contentDescription,
            Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
        )

        Box(
            Modifier.fillMaxWidth(),
            Alignment.Center
        ) {
            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.typography.bodyLarge,
                LocalContentColor provides MaterialTheme.colorScheme.onBackground,
            ) {
                message()
            }
        }
    }
}


