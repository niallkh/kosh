package kosh.domain.models.token

import androidx.compose.runtime.Immutable
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.NetworkEntity
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class BalanceFilters(
    val filterAccounts: Set<AccountEntity.Id>? = null,
    val networks: Set<NetworkEntity.Id>? = null,
)
