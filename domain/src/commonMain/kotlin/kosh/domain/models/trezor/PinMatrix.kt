package kosh.domain.models.trezor

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@Immutable
@JvmInline
value class PinMatrix(val value: String)
