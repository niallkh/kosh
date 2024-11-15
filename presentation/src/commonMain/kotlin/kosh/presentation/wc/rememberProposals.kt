package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
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

    return remember {
        object : ProposalsState {
            override val proposals get() = proposals.result
            override fun retry() {
                proposals.retry()
            }
        }
    }
}

@Stable
interface ProposalsState {
    val proposals: ImmutableList<WcSessionProposal>
    fun retry()
}
