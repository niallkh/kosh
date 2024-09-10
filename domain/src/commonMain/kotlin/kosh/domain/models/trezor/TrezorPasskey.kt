package kosh.domain.models.trezor

import androidx.compose.runtime.Immutable
import kosh.domain.models.ByteString
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Immutable
@Serializable
data class TrezorPasskey(
    val index: Index,
    val id: Id,
    val rpId: String? = null,
    val rpName: String? = null,
    val userId: ByteString? = null,
    val userName: String? = null,
    val userDisplayName: String? = null,
    val creationTime: Int? = null,
    val hmacSecret: Boolean? = null,
    val useSignCount: Boolean? = null,
    val algorithm: Int? = null,
    val curve: Int? = null,
) {

    @JvmInline
    @Immutable
    @Serializable
    value class Index(val value: Int)

    @JvmInline
    @Immutable
    @Serializable
    value class Id(val value: ByteString)
}
