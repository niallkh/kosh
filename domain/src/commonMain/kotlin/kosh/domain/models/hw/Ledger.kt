package kosh.domain.models.hw

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Ledger(
    override val id: HardwareWallet.Id,
    val product: String?,
    override val transport: HardwareWallet.Transport,
) : HardwareWallet
