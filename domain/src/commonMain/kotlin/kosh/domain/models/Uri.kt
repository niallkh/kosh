package kosh.domain.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import com.eygraber.uri.Uri.Companion as LibUri

@JvmInline
@Immutable
@Serializable
value class Uri private constructor(
    internal val value: String,
) {
    companion object {
        operator fun invoke(value: String): Uri {
            checkNotNull(LibUri.parseOrNull(value))
            return Uri(value)
        }
    }
}

fun Uri.toLibUri() = LibUri.parse(value)
