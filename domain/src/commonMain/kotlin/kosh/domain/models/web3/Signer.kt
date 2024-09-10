package kosh.domain.models.web3

import androidx.compose.runtime.Immutable
import kosh.domain.entities.WalletEntity
import kosh.domain.models.Address
import kosh.domain.models.account.DerivationPath
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Signer(
    val location: WalletEntity.Location,
    val address: Address,
    val derivationPath: DerivationPath,
)
