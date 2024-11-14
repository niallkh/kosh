package kosh.ui.reown

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.raise.nullable
import kosh.domain.models.Redirect
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.uuid.leastSignificantBits
import kosh.presentation.account.AccountMultiSelectorState
import kosh.presentation.account.rememberAccountMultiSelector
import kosh.presentation.network.NetworkMultiSelectorState
import kosh.presentation.network.rememberNetworkMultiSelector
import kosh.presentation.wc.ApproveProposalState
import kosh.presentation.wc.ProposalState
import kosh.presentation.wc.RejectProposalState
import kosh.presentation.wc.rememberApproveProposal
import kosh.presentation.wc.rememberProposal
import kosh.presentation.wc.rememberRejectProposal
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.button.LoadingButton
import kosh.ui.component.button.LoadingTextButton
import kosh.ui.component.button.PrimaryButtons
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.dapp.DappTitle
import kosh.ui.component.items.AccountItem
import kosh.ui.component.items.NetworkItem
import kosh.ui.component.items.VerifyContextItem
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.text.Header
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage
import kosh.ui.resources.Res
import kosh.ui.resources.wc_proposal_approve_btn
import kosh.ui.resources.wc_proposal_reject_btn
import kotlinx.collections.immutable.persistentSetOf
import org.jetbrains.compose.resources.stringResource

@Composable
fun WcProposalScreen(
    id: WcSessionProposal.Id,
    requestId: Long,
    onFinish: (Redirect?) -> Unit,
    onCancel: () -> Unit,
    onNavigateUp: () -> Unit,
    proposal: ProposalState = rememberProposal(id, requestId),
    reject: RejectProposalState = rememberRejectProposal(id, onCancel),
    approve: ApproveProposalState = rememberApproveProposal(id, onFinish),
    accountSelector: AccountMultiSelectorState = rememberAccountMultiSelector(),
    networkSelector: NetworkMultiSelectorState = rememberNetworkMultiSelector(
        networkIds = proposal.proposal?.availableNetworks ?: persistentSetOf(),
        requiredIds = proposal.proposal?.requiredNetworks ?: persistentSetOf()
    ),
) {
    KoshScaffold(
        title = {
            if (proposal.failure == null) {
                DappTitle(proposal.proposal?.proposal?.dapp)
            }
        },

        actions = {
            if (proposal.failure == null) {
                DappIcon(proposal.proposal?.proposal?.dapp)
                Spacer(Modifier.width(8.dp))
            }
        },
        onNavigateUp = onNavigateUp
    ) { paddingValues ->

        AppFailureMessage(approve.failure)

        AppFailureMessage(reject.failure)

        WcSessionProposalContent(
            proposal = proposal,
            networkSelector = networkSelector,
            accountSelector = accountSelector,
            onReject = { reject() },
            rejecting = reject.rejecting,
            approving = approve.approving,
            onApprove = {
                nullable {
                    approve(
                        ensureNotNull(accountSelector).accounts
                            .filter { it.id in accountSelector.selected },
                        ensureNotNull(networkSelector).networks
                            .filter { it.id in networkSelector.selected },
                    )
                }
            },
            contentPadding = paddingValues
        )

        LoadingIndicator(
            proposal.loading,
            Modifier.padding(paddingValues),
        )
    }
}

@Composable
fun WcSessionProposalContent(
    proposal: ProposalState,
    networkSelector: NetworkMultiSelectorState,
    accountSelector: AccountMultiSelectorState,
    approving: Boolean,
    rejecting: Boolean,
    onApprove: () -> Unit,
    onReject: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    proposal.failure?.let {
        AppFailureItem(it, Modifier.padding(contentPadding)) { proposal.retry() }
    } ?: LazyColumn(
        contentPadding = contentPadding
    ) {

        item {
            proposal.proposal?.proposal?.let {
                VerifyContextItem(it.verifyContext)
            }
        }

        stickyHeader {
            Header("Networks")
        }

        items(
            items = networkSelector.networks,
            key = { it.id.value.leastSignificantBits }
        ) { network ->
            NetworkItem(
                network = network,
                onClick = { networkSelector.invoke(network.id) },
            ) {
                Checkbox(
                    checked = network.id in networkSelector.selected,
                    onCheckedChange = { networkSelector.invoke(network.id) },
                    enabled = network.id !in networkSelector.required
                )
            }
        }

        stickyHeader {
            Header("Accounts")
        }

        items(
            items = accountSelector.accounts,
            key = { it.id.value.leastSignificantBits }
        ) { account ->
            AccountItem(
                account = account,
                onClick = { accountSelector.select(account.id) },
            ) {
                Checkbox(
                    checked = account.id in accountSelector.selected,
                    onCheckedChange = { accountSelector.select(account.id) }
                )
            }
        }

        item {
            PrimaryButtons(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),

                cancel = {
                    LoadingTextButton(rejecting, onReject) {
                        Text(stringResource(Res.string.wc_proposal_reject_btn))
                    }
                },
                confirm = {
                    LoadingButton(approving, onApprove) {
                        Text(stringResource(Res.string.wc_proposal_approve_btn))
                    }
                }
            )
        }

        item {
            Spacer(Modifier.height(128.dp))
        }
    }
}
