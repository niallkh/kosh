package kosh.domain.models.hw

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Immutable
@Serializable
sealed interface HardwareWallet {
    val id: Id
    val transport: Transport

    @JvmInline
    @Immutable
    @Serializable
    value class Id(val value: String)

    enum class Transport {
        USB,
        BLE,
    }
}
