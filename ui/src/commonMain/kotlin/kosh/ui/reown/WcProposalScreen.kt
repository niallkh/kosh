package kosh.ui.reown

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.raise.nullable
import arrow.optics.Getter
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.state.AppState
import kosh.domain.state.accounts
import kosh.domain.state.networks
import kosh.presentation.account.AccountMultiSelectorState
import kosh.presentation.account.rememberAccountMultiSelector
import kosh.presentation.network.NetworkMultiSelectorState
import kosh.presentation.network.rememberNetworkMultiSelector
import kosh.presentation.reown.ApproveProposalState
import kosh.presentation.reown.ProposalState
import kosh.presentation.reown.rememberApproveProposal
import kosh.presentation.reown.rememberProposal
import kosh.presentation.reown.rememberRejectProposal
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.button.LoadingButton
import kosh.ui.component.button.PrimaryButtons
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.dapp.DappTitle
import kosh.ui.component.dapp.VerifyContextItem
import kosh.ui.component.network.NetworkItem
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.text.Header
import kosh.ui.component.wallet.AccountItem
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage
import kosh.ui.navigation.BackHandler
import kosh.ui.resources.Res
import kosh.ui.resources.wc_proposal_approve_btn
import kosh.ui.resources.wc_proposal_reject_btn
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.resources.stringResource

@Composable
fun WcProposalScreen(
    id: WcSessionProposal.Id,
    requestId: Long,
    onResult: () -> Unit,
    onCancel: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    val proposal = rememberProposal(id, requestId)

    val reject = rememberRejectProposal(id)

    val approve = rememberApproveProposal(id)

    val accountSelector = proposal.proposal?.let {
        rememberAccountMultiSelector(
            optic = AppState.accounts compose Getter { it.values.toPersistentList() },
        )
    }

    val networkSelector = proposal.proposal?.let { proposalAggregated ->
        rememberNetworkMultiSelector(
            initial = AppState.networks compose Getter { map ->
                map.values.filter { it.id in proposalAggregated.networks }.toPersistentList()
            },
            initialRequired = proposalAggregated.required,
        )
    }

    AppFailureMessage(approve.failure) {
        approve.retry()
    }

    LaunchedEffect(approve.approved) {
        if (approve.approved) {
            onResult()
        }
    }

    LaunchedEffect(reject.rejected) {
        if (reject.rejected) {
            onCancel()
        }
    }

    BackHandler { reject.reject() }

    WcSessionProposalContent(
        proposal = proposal,
        networkSelector = networkSelector,
        accountSelector = accountSelector,
        approve = approve,
        onNavigateUp = onNavigateUp,
        onReject = { reject.reject() }
    )
}

@Composable
fun WcSessionProposalContent(
    proposal: ProposalState,
    networkSelector: NetworkMultiSelectorState?,
    accountSelector: AccountMultiSelectorState?,
    approve: ApproveProposalState,
    onNavigateUp: () -> Unit,
    onReject: () -> Unit,
) {
    KoshScaffold(
        title = {
            if (proposal.failure == null) {
                DappTitle(
                    proposal.proposal?.proposal?.dapp?.name,
                    proposal.proposal?.proposal?.dapp?.url,
                )
            }
        },

        actions = {
            if (proposal.failure == null) {
                DappIcon(
                    proposal.proposal?.proposal?.dapp?.url,
                    proposal.proposal?.proposal?.dapp?.icon,
                )
                Spacer(Modifier.width(8.dp))
            }
        },
        onNavigateUp = { onNavigateUp() }
    ) { innerPadding ->
        proposal.failure?.let {
            AppFailureItem(it, Modifier.padding(innerPadding)) { proposal.retry() }
        } ?: LazyColumn(
            contentPadding = innerPadding
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
                items = networkSelector?.available.orEmpty(),
                key = { it.id.value.leastSignificantBits }
            ) { network ->
                NetworkItem(
                    network = network,
                    onClick = { networkSelector?.select?.invoke(network.id) },
                ) {
                    Checkbox(
                        checked = network.id in networkSelector?.selected.orEmpty(),
                        onCheckedChange = { networkSelector?.select?.invoke(network.id) },
                        enabled = network.id !in networkSelector?.required.orEmpty()
                    )
                }
            }

            stickyHeader {
                Header("Accounts")
            }

            items(
                items = accountSelector?.available.orEmpty(),
                key = { it.id.value.leastSignificantBits }
            ) { account ->
                AccountItem(
                    account = account,
                    onClick = { accountSelector?.select?.invoke(account.id) },
                ) {
                    Checkbox(
                        checked = account.id in accountSelector?.selected.orEmpty(),
                        onCheckedChange = { accountSelector?.select?.invoke(account.id) }
                    )
                }
            }

            item {
                PrimaryButtons(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    cancel = {
                        TextButton(onReject) {
                            Text(stringResource(Res.string.wc_proposal_reject_btn))
                        }
                    },
                    confirm = {
                        LoadingButton(approve.loading, onClick = {
                            nullable {
                                approve.approve(
                                    ensureNotNull(accountSelector).available
                                        .filter { it.id in accountSelector.selected }
                                        .map { it.address },
                                    ensureNotNull(networkSelector).available
                                        .filter { it.id in networkSelector.selected }
                                        .map { it.chainId },
                                )

                            }
                        }) {
                            Text(stringResource(Res.string.wc_proposal_approve_btn))
                        }
                    }
                )
            }

            item {
                Spacer(Modifier.height(64.dp))
            }
        }

        LoadingIndicator(
            proposal.loading,
            Modifier.padding(innerPadding),
        )
    }
}

