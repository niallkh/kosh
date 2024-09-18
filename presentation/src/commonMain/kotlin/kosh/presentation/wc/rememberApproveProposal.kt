package kosh.presentation.wc

import androidx.compose.runtime.Composable
import kosh.domain.failure.WcFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.wc.WcProposal
import kosh.domain.usecases.wc.WcProposalService
import kosh.presentation.Perform
import kosh.presentation.di.di

@Composable
fun rememberApproveProposal(
    id: WcProposal.Id,
    proposalService: WcProposalService = di { domain.wcProposalService },
): ApproveProposalState {

    val approve = Perform(id) { (accounts, chains): Pair<List<Address>, List<ChainId>> ->
        proposalService.approve(id, accounts, chains).bind()
    }

    return ApproveProposalState(
        approved = approve.performed,
        loading = approve.inProgress,
        failure = approve.failure,
        retry = { approve.retry() },
        approve = { accounts, chains -> approve(accounts to chains) },
    )
}

data class ApproveProposalState(
    val approved: Boolean,
    val loading: Boolean,
    val failure: WcFailure?,
    val retry: () -> Unit,
    val approve: (List<Address>, List<ChainId>) -> Unit,
)
