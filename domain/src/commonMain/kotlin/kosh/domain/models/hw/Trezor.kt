package kosh.domain.models.hw

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Trezor(
    override val id: HardwareWallet.Id,
    val product: String?,
) : HardwareWallet {

    override val transport: HardwareWallet.Transport
        get() = HardwareWallet.Transport.USB
}
