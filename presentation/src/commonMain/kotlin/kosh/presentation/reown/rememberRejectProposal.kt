package kosh.presentation.reown

import androidx.compose.runtime.Composable
import kosh.domain.failure.AppFailure
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.usecases.reown.WcProposalService
import kosh.presentation.Perform
import kosh.presentation.core.di

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
