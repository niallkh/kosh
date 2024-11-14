package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.WcFailure
import kosh.domain.models.Redirect
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.usecases.reown.WcProposalService
import kosh.presentation.core.di
import kosh.presentation.rememberEitherEffect

@Composable
fun rememberApproveProposal(
    id: WcSessionProposal.Id,
    onApproved: (Redirect?) -> Unit,
    proposalService: WcProposalService = di { domain.wcProposalService },
): ApproveProposalState {

    val approve = rememberEitherEffect(
        id,
        onFinish = onApproved
    ) { (accounts, chains): Pair<List<AccountEntity>, List<NetworkEntity>> ->
        proposalService.approve(id, accounts, chains).bind()
    }

    return remember {
        object : ApproveProposalState {
            override val approving: Boolean get() = approve.inProgress
            override val failure: WcFailure? get() = approve.failure
            override operator fun invoke(
                accounts: List<AccountEntity>,
                chains: List<NetworkEntity>,
            ) {
                approve(accounts to chains)
            }
        }
    }
}

@Stable
interface ApproveProposalState {
    val approving: Boolean
    val failure: WcFailure?
    operator fun invoke(accounts: List<AccountEntity>, chains: List<NetworkEntity>)
}
