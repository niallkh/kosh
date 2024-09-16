package kosh.ui.wc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.raise.nullable
import kosh.domain.failure.AccountFailure
import kosh.domain.failure.NetworkFailure
import kosh.domain.models.wc.WcAuthentication
import kosh.presentation.account.AccountSelectorState
import kosh.presentation.account.rememberAccountSelector
import kosh.presentation.models.SignRequest
import kosh.presentation.network.NetworkSelectorState
import kosh.presentation.network.rememberNetworkSelector
import kosh.presentation.wc.AuthenticationRequestState
import kosh.presentation.wc.rememberAuthenticationRequest
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.bottomsheet.KoshModalBottomSheet
import kosh.ui.component.button.LoadingButton
import kosh.ui.component.button.PrimaryButtons
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.dapp.DappTitle
import kosh.ui.component.dapp.VerifyContextItem
import kosh.ui.component.network.NetworkItem
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.single.single
import kosh.ui.component.text.Header
import kosh.ui.component.wallet.AccountItem
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
    onResult: () -> Unit,
    onCancel: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    val accountSelector = rememberAccountSelector()

    val networkSelector = rememberNetworkSelector()

    val authenticationRequest = rememberAuthenticationRequest(
        id, accountSelector.selected, networkSelector.selected
    )

    val sign = SignContent(accountSelector.selected?.address)

    LaunchedEffect(sign.signedRequest) {
        sign.signedRequest?.let {
            authenticationRequest.send(it.request as SignRequest.SignPersonal, it.signature)
        }
    }

    LaunchedEffect(authenticationRequest.sent) {
        if (authenticationRequest.sent) {
            onResult()
        }
    }

    AppFailureMessage(authenticationRequest.txFailure) {
        authenticationRequest.retry()
    }

    WcAuthenticationContent(
        authentication = authenticationRequest,
        signing = sign.signing,
        onSign = { sign.sign(it) },
        onReject = {
            authenticationRequest.reject()
            onCancel()
        },
        onNavigateUp = onNavigateUp,
        networkSelector = networkSelector,
        accountSelector = accountSelector,
    )
}

@Composable
fun WcAuthenticationContent(
    authentication: AuthenticationRequestState,
    accountSelector: AccountSelectorState,
    networkSelector: NetworkSelectorState,
    signing: Boolean,
    onNavigateUp: () -> Unit,
    onSign: (SignRequest.SignPersonal) -> Unit,
    onReject: () -> Unit,
) {
    KoshScaffold(
        title = {
            if (authentication.failure == null) {
                DappTitle(authentication.auth?.dapp)
            }
        },
        onUp = { onNavigateUp() },

        actions = {
            if (authentication.failure == null) {
                DappIcon(authentication.auth?.dapp)
            }
            Spacer(Modifier.width(8.dp))
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            authentication.failure?.let {
                AppFailureItem(it) { authentication.retry() }
            } ?: run {
                authentication.auth?.let {
                    VerifyContextItem(it.verifyContext)
                }

                NetworkSelector(networkSelector)

                AccountSelector(accountSelector)

                AuthenticationMessage(authentication.message)

                PrimaryButtons(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    cancel = {
                        TextButton(onClick = onReject.single()) {
                            Text(stringResource(Res.string.wc_authentication_reject_btn))
                        }
                    },
                    confirm = {
                        LoadingButton(signing, onClick = {
                            nullable {
                                onSign(
                                    SignRequest.SignPersonal(
                                        account = ensureNotNull(authentication.message).account,
                                        message = ensureNotNull(authentication.message).msg,
                                        chainId = ensureNotNull(authentication.message).chainId,
                                        dapp = ensureNotNull(authentication.auth).dapp,
                                    )
                                )
                            }
                        }) {
                            Text(stringResource(Res.string.wc_authentication_authenticate_btn))
                        }
                    },
                )
            }
        }

        LoadingIndicator(authentication.loading)
    }
}

@Composable
fun AccountSelector(
    accountSelector: AccountSelectorState,
    modifier: Modifier = Modifier,
) {
    var openAccountSelector by remember { mutableStateOf(false) }

    accountSelector.selected?.let {
        AccountItem(
            modifier = modifier,
            account = it,
            onClick = { openAccountSelector = true },
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
    modifier: Modifier = Modifier,
) {
    var openNetworkSelector by remember { mutableStateOf(false) }

    networkSelector.selected?.let {
        NetworkItem(
            modifier = modifier,
            network = it,
            onClick = { openNetworkSelector = true },
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
