package kosh.ui.token

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.domain.entities.TokenEntity
import kosh.presentation.token.rememberDeleteToken
import kosh.ui.component.scaffold.KoshScaffold
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
fun DeleteTokenScreen(
    id: TokenEntity.Id,
    onFinish: () -> Unit,
) {
    val onFinishUpdated by rememberUpdatedState(onFinish)

    val deleteToken = rememberDeleteToken(id)

    LaunchedEffect(Unit) {
        delay(300.milliseconds)
        deleteToken.delete()
    }

    LaunchedEffect(deleteToken.deleted) {
        if (deleteToken.deleted) {
            delay(1.seconds)
            onFinishUpdated()
        }
    }

    KoshScaffold(
        title = { Text("Delete Token") },
        onUp = null,
    ) {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            AnimatedContent(
                targetState = deleteToken.deleted,
            ) { deleted ->
                if (deleted) {
                    Icon(
                        Icons.Filled.CheckCircle,
                        "Deleted",
                        Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                } else {
                    CircularProgressIndicator()
                }
            }
        }
    }

}