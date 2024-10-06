package kosh.presentation.wc

import androidx.compose.runtime.Composable
import kosh.domain.failure.AppFailure
import kosh.domain.models.wc.WcSessionProposal
import kosh.domain.usecases.wc.WcProposalService
import kosh.presentation.Perform
import kosh.presentation.di.di

@Composable
fun rememberRejectProposal(
    id: WcSessionProposal.Id,
    proposalService: WcProposalService = di { domain.wcProposalService },
): RejectProposalState {
    val reject = Perform<Unit, AppFailure>(id) {
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
