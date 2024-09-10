package kosh.ui.component.path

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.ByteString
import okio.Path

fun interface PathResolver : suspend (Path) -> ByteString

@Composable
fun <T> Path.resolve(transform: (ByteString) -> T): State<T?> {
    val pathResolver = LocalPathResolver.current

    return produceState<T?>(null) {
        value = withContext(Dispatchers.Default) {
            pathResolver(this@resolve).let(transform)
        }
    }
}

val LocalPathResolver = staticCompositionLocalOf<PathResolver> {
    error("LocalPathResolver not provided")
}
