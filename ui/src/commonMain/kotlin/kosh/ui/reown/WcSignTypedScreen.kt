package kosh.ui.reown

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.raise.nullable
import kosh.domain.models.reown.WcRequest
import kosh.presentation.account.AccountState
import kosh.presentation.account.rememberAccount
import kosh.presentation.models.SignRequest
import kosh.presentation.network.NetworkState
import kosh.presentation.network.rememberNetwork
import kosh.presentation.wc.RejectRequestState
import kosh.presentation.wc.SendTypedDataState
import kosh.presentation.wc.SignTypedRequestState
import kosh.presentation.wc.rememberRejectRequest
import kosh.presentation.wc.rememberSendTypedData
import kosh.presentation.wc.rememberSignTypedRequest
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
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage
import kosh.ui.resources.Res
import kosh.ui.resources.wc_request_reject_btn
import kosh.ui.resources.wc_request_sign_btn
import kosh.ui.transaction.SignContent
import kosh.ui.transaction.TypedMessageCard
import kosh.ui.transaction.TypedMessageDomainCard
import org.jetbrains.compose.resources.stringResource

@Composable
fun WcSignTypedScreen(
    id: WcRequest.Id,
    onCancel: () -> Unit,
    onFinish: () -> Unit,
    onNavigateUp: () -> Unit,
    signTyped: SignTypedRequestState = rememberSignTypedRequest(id),
    network: NetworkState? = signTyped.call?.chainId?.let { rememberNetwork(it) },
    account: AccountState? = signTyped.call?.let { rememberAccount(it.account) },
    sendTypedData: SendTypedDataState = rememberSendTypedData(id, onFinish),
    rejectRequest: RejectRequestState = rememberRejectRequest(id, onCancel),
) {
    KoshScaffold(
        title = {
            if (signTyped.failure == null) {
                DappTitle(signTyped.request?.dapp)
            }
        },
        onNavigateUp = onNavigateUp,
        actions = {
            if (signTyped.failure == null) {
                DappIcon(signTyped.request?.dapp)
            }
            Spacer(Modifier.width(8.dp))
        }
    ) { innerPadding ->

        val sign = SignContent(account?.entity?.walletId) {
            sendTypedData.send(it.request as SignRequest.SignTyped, it.signature)
        }

        AppFailureMessage(sign.failure)

        AppFailureMessage(sendTypedData.failure)

        AppFailureMessage(rejectRequest.failure)

        val signing by remember {
            derivedStateOf { sign.signing || sendTypedData.sending }
        }

        WcSignTypedContent(
            signTyped = signTyped,
            signing = signing,
            onSign = {
                nullable {
                    val request = SignRequest.SignTyped(
                        chainId = ensureNotNull(signTyped.call).chainId,
                        dapp = ensureNotNull(signTyped.request).dapp,
                        account = ensureNotNull(signTyped.call).account,
                        json = ensureNotNull(signTyped.call).json,
                    )
                    sign(request)
                }
            },
            onReject = rejectRequest::invoke,
            contentPadding = innerPadding,
            account = account,
            network = network,
            rejecting = rejectRequest.rejecting
        )

        LoadingIndicator(
            signTyped.loading,
            Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun WcSignTypedContent(
    signTyped: SignTypedRequestState,
    account: AccountState?,
    network: NetworkState?,
    signing: Boolean,
    rejecting: Boolean,
    onSign: () -> Unit,
    onReject: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    signTyped.failure?.let {
        AppFailureItem(it, Modifier.padding(contentPadding)) { signTyped.retry() }
    } ?: run {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            signTyped.failure?.let {
                AppFailureItem(it) { signTyped.retry() }
            } ?: run {
                signTyped.request?.let {
                    VerifyContextItem(it.verifyContext)
                }

                Column {
                    if (network != null) {
                        NetworkItem(network.entity)
                    }

                    AccountItem(account?.entity)
                }

                TypedMessageDomainCard(
                    jsonText = signTyped.call?.json?.json,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                TypedMessageCard(
                    jsonText = signTyped.call?.json?.json,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )

                PrimaryButtons(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    cancel = {
                        LoadingTextButton(rejecting, onReject) {
                            Text(stringResource(Res.string.wc_request_reject_btn))
                        }
                    },
                    confirm = {
                        LoadingButton(signing, onSign) {
                            Text(stringResource(Res.string.wc_request_sign_btn))
                        }
                    }
                )
            }
        }

        Spacer(Modifier.size(128.dp))
    }
}
