package kosh.domain.models.reown

import androidx.compose.runtime.Immutable
import kosh.domain.entities.NetworkEntity
import kosh.domain.serializers.ImmutableSet
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class WcProposalAggregated(
    val proposal: WcSessionProposal,
    val networks: ImmutableSet<NetworkEntity.Id>,
    val required: ImmutableSet<NetworkEntity.Id>,
)
