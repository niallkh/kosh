package kosh.presentation.wc

import androidx.compose.runtime.Composable
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcProposalAggregated
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.usecases.reown.WcProposalService
import kosh.presentation.core.di
import kosh.presentation.rememberLoad

@Composable
fun rememberProposal(
    id: WcSessionProposal.Id,
    requestId: Long,
    proposalService: WcProposalService = di { domain.wcProposalService },
): ProposalState {
    val content = rememberLoad(id, requestId) {
        proposalService.get(id, requestId).bind()
    }

    return ProposalState(
        loading = content.loading,
        proposal = content.result,
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
