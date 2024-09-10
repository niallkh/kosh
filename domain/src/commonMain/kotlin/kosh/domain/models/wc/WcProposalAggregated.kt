package kosh.domain.models.wc

import androidx.compose.runtime.Immutable
import kosh.domain.entities.NetworkEntity
import kosh.domain.serializers.ImmutableSet
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class WcProposalAggregated(
    val proposal: WcProposal,
    val networks: ImmutableSet<NetworkEntity.Id>,
    val required: ImmutableSet<NetworkEntity.Id>,
)
