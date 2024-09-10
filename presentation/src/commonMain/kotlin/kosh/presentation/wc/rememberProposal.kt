package kosh.presentation.wc

import androidx.compose.runtime.Composable
import kosh.domain.failure.WcFailure
import kosh.domain.models.wc.WcProposal
import kosh.domain.models.wc.WcProposalAggregated
import kosh.domain.usecases.wc.WcProposalService
import kosh.presentation.LoadContent
import kosh.presentation.di.di

@Composable
fun rememberProposal(
    id: WcProposal.Id,
    requestId: Long,
    proposalService: WcProposalService = di { domain.wcProposalService },
): ProposalState {

    val content = LoadContent(id, requestId) {
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
