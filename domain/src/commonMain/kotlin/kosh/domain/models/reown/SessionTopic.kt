package kosh.domain.models.reown

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
@Immutable
value class SessionTopic(val value: String)
