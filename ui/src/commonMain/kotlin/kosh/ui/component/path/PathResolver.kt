package kosh.ui.component.path

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import kosh.domain.models.ByteString
import kosh.domain.serializers.Path
import kosh.presentation.rememberLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface PathResolver : suspend (Path) -> ByteString

@Composable
inline fun <reified T : Any> Path.resolve(
    crossinline transform: (ByteString) -> T,
): T? {
    val pathResolver = LocalPathResolver.current

    return rememberLoad(this) {
        withContext(Dispatchers.Default) {
            pathResolver(this@resolve).let(transform)
        }
    }.result
}

val LocalPathResolver = staticCompositionLocalOf<PathResolver> {
    error("LocalPathResolver not provided")
}
