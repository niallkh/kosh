package kosh.domain.models.ledger

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Immutable
@Serializable
data class Ledger(
    val id: Id,
    val product: String?,
) {

    @JvmInline
    @Immutable
    @Serializable
    value class Id(val value: String)
}

