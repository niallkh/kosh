package kosh.domain.models.reown

import androidx.compose.runtime.Immutable
import kosh.domain.entities.NetworkEntity
import kosh.domain.serializers.ImmutableSet
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class WcProposalAggregated(
    val proposal: WcSessionProposal,
    val availableNetworks: ImmutableSet<NetworkEntity.Id>,
    val requiredNetworks: ImmutableSet<NetworkEntity.Id>,
)
