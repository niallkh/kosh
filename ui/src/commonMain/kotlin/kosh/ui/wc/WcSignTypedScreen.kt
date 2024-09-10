package kosh.ui.wc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.raise.nullable
import kosh.domain.models.wc.WcRequest
import kosh.presentation.account.rememberAccount
import kosh.presentation.models.SignRequest
import kosh.presentation.network.rememberNetwork
import kosh.presentation.wc.SignTypedRequestState
import kosh.presentation.wc.rememberSignTypedRequest
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.button.LoadingButton
import kosh.ui.component.button.PrimaryButtons
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.dapp.DappTitle
import kosh.ui.component.dapp.VerifyContextItem
import kosh.ui.component.network.NetworkItem
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.single.single
import kosh.ui.component.wallet.AccountItem
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
    onResult: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    val signTyped = rememberSignTypedRequest(id)

    val sign = SignContent(signTyped.call?.account)

    LaunchedEffect(sign.signedRequest) {
        sign.signedRequest?.let {
            signTyped.send(it.request as SignRequest.SignTyped, it.signature)
        }
    }

    LaunchedEffect(signTyped.sent) {
        if (signTyped.sent) {
            onResult()
        }
    }

    AppFailureMessage(signTyped.txFailure) {
        signTyped.retry()
    }

    WcSignTypedContent(
        signTyped = signTyped,
        signing = sign.signing,
        onSign = { sign.sign(it) },
        onReject = {
            signTyped.reject()
            onCancel()
        },
        onNavigateUp = onNavigateUp,
    )
}

@Composable
fun WcSignTypedContent(
    signTyped: SignTypedRequestState,
    signing: Boolean,
    onNavigateUp: () -> Unit,
    onSign: (SignRequest.SignTyped) -> Unit,
    onReject: () -> Unit,
) {
    KoshScaffold(
        title = {
            if (signTyped.failure == null) {
                DappTitle(signTyped.request?.dapp)
            }
        },
        onUp = onNavigateUp,
        largeTopBar = true,
        actions = {
            if (signTyped.failure == null) {
                DappIcon(signTyped.request?.dapp)
            }
            Spacer(Modifier.width(8.dp))
        }
    ) {
        signTyped.failure?.let {
            AppFailureItem(it) { signTyped.retry() }
        } ?: run {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                signTyped.request?.let {
                    VerifyContextItem(it.verifyContext)
                }

                Column {
                    val network = signTyped.call?.chainId?.let { rememberNetwork(it) }

                    if (signTyped.call == null || signTyped.call?.chainId != null) {
                        NetworkItem(network?.entity)
                    }

                    val account = signTyped.call?.let { rememberAccount(it.account) }

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
                        TextButton(onClick = onReject.single()) {
                            Text(stringResource(Res.string.wc_request_reject_btn))
                        }
                    },
                    confirm = {
                        LoadingButton(signing, onClick = {
                            nullable {
                                val request = SignRequest.SignTyped(
                                    chainId = ensureNotNull(signTyped.call).chainId,
                                    dapp = ensureNotNull(signTyped.request).dapp,
                                    account = ensureNotNull(signTyped.call).account,
                                    json = ensureNotNull(signTyped.call).json,
                                )
                                onSign(request)
                            }
                        }) {
                            Text(stringResource(Res.string.wc_request_sign_btn))
                        }
                    }
                )
            }
        }

        LoadingIndicator(signTyped.loading)
    }
}
