package kosh.presentation.reown

import androidx.compose.runtime.Composable
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.models.reown.WcProposalAggregated
import kosh.domain.usecases.reown.WcProposalService
import kosh.presentation.Load
import kosh.presentation.di.di

@Composable
fun rememberProposal(
    id: WcSessionProposal.Id,
    requestId: Long,
    proposalService: WcProposalService = di { domain.wcProposalService },
): ProposalState {

    val content = Load(id, requestId) {
        proposalService.get(id, requestId).bind()
    }

    return ProposalState(
        loading = content.loading,
        proposal = content.content,
        failure = content.failure,
        retry = { content.retry() },
    )
}

data class ProposalState(
    val proposal: WcProposalAggregated?,
    val loading: Boolean,
    val failure: WcFailure?,
    val retry: () -> Unit,
)
