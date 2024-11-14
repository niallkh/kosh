package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.usecases.reown.WcProposalService
import kosh.presentation.core.di
import kosh.presentation.rememberEitherEffect

@Composable
fun rememberRejectProposal(
    id: WcSessionProposal.Id,
    onRejected: () -> Unit,
    proposalService: WcProposalService = di { domain.wcProposalService },
): RejectProposalState {
    val reject = rememberEitherEffect(id, onFinish = { onRejected() }) { _: Unit ->
        proposalService.reject(id).bind()
    }

    return remember {
        object : RejectProposalState {
            override val rejecting: Boolean = reject.inProgress
            override val failure: WcFailure? get() = reject.failure
            override operator fun invoke() = reject(Unit)
        }
    }
}

@Stable
interface RejectProposalState {
    val rejecting: Boolean
    val failure: WcFailure?
    operator fun invoke()
}
