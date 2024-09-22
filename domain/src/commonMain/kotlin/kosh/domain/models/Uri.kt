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

    override fun toString(): String = value

    companion object {
        private val EMPTY = Uri(com.eygraber.uri.Uri.EMPTY.toString())

        operator fun invoke(value: String): Uri {
            checkNotNull(LibUri.parseOrNull(value))
            return Uri(value)
        }

        operator fun invoke(): Uri = EMPTY
    }
}

fun Uri.toLibUri() = LibUri.parse(value)
