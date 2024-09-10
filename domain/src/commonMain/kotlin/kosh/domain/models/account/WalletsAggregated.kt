package kosh.domain.models.account

import androidx.compose.runtime.Immutable
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.WalletEntity
import kosh.domain.serializers.ImmutableList
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class WalletAggregated(
    val wallet: WalletEntity,
    val accounts: ImmutableList<AccountEntity>,
)
