package kosh.domain.models.keystore

import kotlin.jvm.JvmInline

@JvmInline
value class CipherWrapper(
    val value: Any,
)

data class CipherRequest(
    val cipher: CipherWrapper,
    val onlyBiometry: Boolean,
)
