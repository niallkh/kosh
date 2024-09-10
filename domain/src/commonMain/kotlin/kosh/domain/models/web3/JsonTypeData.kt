package kosh.domain.models.web3

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
@Immutable
value class JsonTypeData(val json: String)
