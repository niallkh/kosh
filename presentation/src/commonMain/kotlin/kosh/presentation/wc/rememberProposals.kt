package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kosh.domain.models.wc.WcProposal
import kosh.domain.serializers.ImmutableList
import kosh.domain.serializers.ImmutableListSerializer
import kosh.domain.usecases.wc.WcProposalService
import kosh.presentation.di.di
import kosh.presentation.di.rememberLifecycleState
import kosh.presentation.di.rememberSerializable
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun rememberProposals(
    proposalService: WcProposalService = di { domain.wcProposalService },
): ProposalsState {
    var proposals by rememberSerializable(
        stateSerializer = ImmutableListSerializer(WcProposal.serializer())
    ) { mutableStateOf(persistentListOf()) }

    if (rememberLifecycleState()) {
        LaunchedEffect(Unit) {
            proposalService.proposals.collect {
                proposals = it.toPersistentList()
            }
        }
    }

    return ProposalsState(
        proposals = proposals,
    )
}

data class ProposalsState(
    val proposals: ImmutableList<WcProposal>,
)
