package kosh.presentation.reown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.serializers.ImmutableList
import kosh.domain.usecases.reown.WcProposalService
import kosh.presentation.di.di
import kosh.presentation.di.rememberLifecycleState
import kosh.presentation.di.rememberRetainable
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun rememberProposals(
    proposalService: WcProposalService = di { domain.wcProposalService },
): ProposalsState {
    var proposals by rememberRetainable { mutableStateOf(persistentListOf<WcSessionProposal>()) }

    if (rememberLifecycleState()) {
        LaunchedEffect(Unit) {
            proposalService.proposals.collect {
                proposals = it.toPersistentList()
            }
        }
    }

    return ProposalsState(
        proposals = proposals
    )
}

data class ProposalsState(
    val proposals: ImmutableList<WcSessionProposal>,
)
