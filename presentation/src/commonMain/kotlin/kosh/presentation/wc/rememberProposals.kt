package kosh.presentation.wc

import androidx.compose.runtime.Composable
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.serializers.ImmutableList
import kosh.domain.usecases.reown.WcProposalService
import kosh.presentation.core.di
import kosh.presentation.rememberCollect
import kotlinx.collections.immutable.persistentListOf

@Composable
fun rememberProposals(
    proposalService: WcProposalService = di { domain.wcProposalService },
): ProposalsState {
    val proposals = rememberCollect(persistentListOf()) {
        proposalService.proposals
    }

    return ProposalsState(
        proposals = proposals.result
    )
}

data class ProposalsState(
    val proposals: ImmutableList<WcSessionProposal>,
)
