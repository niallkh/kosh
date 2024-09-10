package kosh.domain.models.trezor

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@Immutable
@JvmInline
value class Passphrase(val value: String?)

val Passphrase.enterOnDevice: Boolean
    inline get() = value == null
