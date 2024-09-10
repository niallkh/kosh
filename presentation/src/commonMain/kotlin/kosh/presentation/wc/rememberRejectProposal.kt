package kosh.presentation.wc

import androidx.compose.runtime.Composable
import kosh.domain.failure.AppFailure
import kosh.domain.models.wc.WcProposal
import kosh.domain.usecases.wc.WcProposalService
import kosh.presentation.PerformAction
import kosh.presentation.di.di

@Composable
fun rememberRejectProposal(
    id: WcProposal.Id,
    proposalService: WcProposalService = di { domain.wcProposalService },
): RejectProposalState {
    val reject = PerformAction<Unit, AppFailure>(id) {
        proposalService.reject(id)
    }

    return RejectProposalState(
        rejected = reject.performed,
        reject = { reject(Unit) }
    )
}

data class RejectProposalState(
    val rejected: Boolean,
    val reject: () -> Unit,
)
