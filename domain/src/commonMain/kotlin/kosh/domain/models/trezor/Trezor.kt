package kosh.domain.models.trezor

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Immutable
@Serializable
data class Trezor(
    val id: Id,
    val product: String?,
) {

    @JvmInline
    @Immutable
    @Serializable
    value class Id(val value: Long)
}
