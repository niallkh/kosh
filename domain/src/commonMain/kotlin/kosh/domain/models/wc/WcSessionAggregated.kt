package kosh.domain.models.wc

import androidx.compose.runtime.Immutable
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.NetworkEntity
import kosh.domain.serializers.ImmutableSet
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class WcSessionAggregated(
    val session: WcSession,
    val availableNetworks: ImmutableSet<NetworkEntity.Id>,
    val requiredNetworks: ImmutableSet<NetworkEntity.Id>,
    val approvedAccounts: Set<AccountEntity.Id>,
    val approvedNetworks: Set<NetworkEntity.Id>,
)
