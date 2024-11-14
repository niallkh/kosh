package kosh.domain.models.reown

import androidx.compose.runtime.Immutable
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.NetworkEntity
import kosh.domain.serializers.ImmutableSet
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class WcSessionAggregated(
    val session: WcSession,
    val approvedAccounts: ImmutableSet<AccountEntity.Id>,
    val availableNetworks: ImmutableSet<NetworkEntity.Id>,
    val requiredNetworks: ImmutableSet<NetworkEntity.Id>,
    val approvedNetworks: ImmutableSet<NetworkEntity.Id>,
)
