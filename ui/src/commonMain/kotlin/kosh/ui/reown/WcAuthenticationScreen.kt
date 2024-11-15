package kosh.ui.reown

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.raise.nullable
import kosh.domain.failure.AccountFailure
import kosh.domain.failure.NetworkFailure
import kosh.domain.models.reown.WcAuthentication
import kosh.presentation.account.AccountSelectorState
import kosh.presentation.account.rememberAccountSelector
import kosh.presentation.models.SignRequest
import kosh.presentation.network.NetworkSelectorState
import kosh.presentation.network.rememberNetworkSelector
import kosh.presentation.wc.ApproveAuthenticationState
import kosh.presentation.wc.AuthenticationRequestState
import kosh.presentation.wc.RejectAuthenticationState
import kosh.presentation.wc.rememberApproveAuthentication
import kosh.presentation.wc.rememberAuthenticationRequest
import kosh.presentation.wc.rememberRejectAuthentication
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.bottomsheet.KoshModalBottomSheet
import kosh.ui.component.button.LoadingButton
import kosh.ui.component.button.LoadingTextButton
import kosh.ui.component.button.PrimaryButtons
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.dapp.DappTitle
import kosh.ui.component.items.AccountItem
import kosh.ui.component.items.NetworkItem
import kosh.ui.component.items.VerifyContextItem
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.text.Header
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage
import kosh.ui.resources.Res
import kosh.ui.resources.wc_authentication_authenticate_btn
import kosh.ui.resources.wc_authentication_reject_btn
import kosh.ui.transaction.SignContent
import org.jetbrains.compose.resources.stringResource

@Composable
fun WcAuthenticationScreen(
    id: WcAuthentication.Id,
    onFinish: () -> Unit,
    onCancel: () -> Unit,
    onNavigateUp: () -> Unit,
    accountSelector: AccountSelectorState = rememberAccountSelector(),
    networkSelector: NetworkSelectorState = rememberNetworkSelector(),
    authentication: AuthenticationRequestState = rememberAuthenticationRequest(
        id,
        accountSelector.selected,
        networkSelector.selected
    ),
    approveAuthentication: ApproveAuthenticationState = rememberApproveAuthentication(
        id,
        accountSelector.selected,
        networkSelector.selected,
        onFinish
    ),
    rejectAuthentication: RejectAuthenticationState = rememberRejectAuthentication(id, onCancel),
) {
    KoshScaffold(
        title = {
            if (authentication.failure == null) {
                DappTitle(authentication.auth?.dapp)
            }
        },
        onNavigateUp = { onNavigateUp() },

        actions = {
            if (authentication.failure == null) {
                DappIcon(authentication.auth?.dapp)
            }
            Spacer(Modifier.width(8.dp))
        }
    ) { paddingValues ->

        val sign = SignContent(accountSelector.selected?.walletId) {
            approveAuthentication.send(it.request as SignRequest.SignPersonal, it.signature)
        }

        AppFailureMessage(sign.failure)

        AppFailureMessage(approveAuthentication.failure)

        AppFailureMessage(rejectAuthentication.failure)

        val signing by remember {
            derivedStateOf { sign.signing || approveAuthentication.approving }
        }

        WcAuthenticationContent(
            authentication = authentication,
            signing = signing,
            onSign = {
                nullable {
                    val signRequest = SignRequest.SignPersonal(
                        account = ensureNotNull(authentication.message).account,
                        message = ensureNotNull(authentication.message).msg,
                        chainId = ensureNotNull(authentication.message).chainId,
                        dapp = ensureNotNull(authentication.auth).dapp,
                    )
                    sign(signRequest)
                }
            },
            onReject = rejectAuthentication::reject,
            rejecting = rejectAuthentication.rejecting,
            networkSelector = networkSelector,
            accountSelector = accountSelector,
            contentPadding = paddingValues,
        )

        LoadingIndicator(
            authentication.loading,
            Modifier.padding(paddingValues),
        )
    }
}

@Composable
fun WcAuthenticationContent(
    authentication: AuthenticationRequestState,
    accountSelector: AccountSelectorState,
    networkSelector: NetworkSelectorState,
    signing: Boolean,
    rejecting: Boolean,
    onSign: () -> Unit,
    onReject: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        authentication.failure?.let {
            AppFailureItem(it) { authentication.retry() }
        } ?: run {
            authentication.auth?.let {
                VerifyContextItem(it.verifyContext)
            }

            NetworkSelector(networkSelector, !signing)

            AccountSelector(accountSelector, !signing)

            AuthenticationMessage(authentication.message)

            PrimaryButtons(
                modifier = Modifier.padding(horizontal = 16.dp),
                cancel = {
                    LoadingTextButton(rejecting, onReject) {
                        Text(stringResource(Res.string.wc_authentication_reject_btn))
                    }
                },
                confirm = {
                    LoadingButton(signing, onSign) {
                        Text(stringResource(Res.string.wc_authentication_authenticate_btn))
                    }
                },
            )
        }

        Spacer(Modifier.size(128.dp))
    }
}


@Composable
fun AccountSelector(
    accountSelector: AccountSelectorState,
    active: Boolean,
    modifier: Modifier = Modifier,
) {
    var openAccountSelector by rememberSaveable { mutableStateOf(false) }

    accountSelector.selected?.let {
        AccountItem(
            modifier = modifier,
            account = it,
            onClick = { openAccountSelector = true }.takeIf { active },
        ) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
        }
    } ?: AppFailureItem(AccountFailure.NoActiveAccounts())

    if (openAccountSelector) {
        KoshModalBottomSheet(
            onDismissRequest = { openAccountSelector = false },
        ) { dismissCallback ->
            Column {
                for (account in accountSelector.available) {
                    key(account.id.value.toString()) {

                        AccountItem(
                            account = account,
                            onClick = {
                                accountSelector.select(account)
                                dismissCallback {}
                            }
                        ) {
                            RadioButton(
                                selected = account.id == accountSelector.selected?.id,
                                onClick = { accountSelector.select(account) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NetworkSelector(
    networkSelector: NetworkSelectorState,
    active: Boolean,
    modifier: Modifier = Modifier,
) {
    var openNetworkSelector by rememberSaveable { mutableStateOf(false) }

    networkSelector.selected?.let {
        NetworkItem(
            modifier = modifier,
            network = it,
            onClick = { openNetworkSelector = true }.takeIf { active },
        ) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
        }
    } ?: AppFailureItem(NetworkFailure.NoActiveNetworks())

    if (openNetworkSelector) {
        KoshModalBottomSheet(
            onDismissRequest = { openNetworkSelector = false },
        ) { dismissCallback ->
            Column {
                for (network in networkSelector.available) {
                    key(network.id.value.toString()) {

                        NetworkItem(
                            network = network,
                            onClick = {
                                networkSelector.select(network)
                                dismissCallback {}
                            }
                        ) {
                            RadioButton(
                                selected = network.id == networkSelector.selected?.id,
                                onClick = { networkSelector.select(network) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AuthenticationMessage(
    message: WcAuthentication.Message?,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {

        Header("Message")

        OutlinedCard(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .placeholder(message == null),
                text = message?.msg?.value ?: "Unknown Authentication Message. ",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
