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
import kosh.presentation.wc.SignPersonalRequestState
import kosh.presentation.wc.rememberSignPersonalRequest
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.button.LoadingButton
import kosh.ui.component.button.PrimaryButtons
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.dapp.DappTitle
import kosh.ui.component.dapp.VerifyContextItem
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.single.single
import kosh.ui.component.wallet.AccountItem
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage
import kosh.ui.resources.Res
import kosh.ui.resources.wc_request_reject_btn
import kosh.ui.resources.wc_request_sign_btn
import kosh.ui.transaction.PersonalMessageCard
import kosh.ui.transaction.SignContent
import org.jetbrains.compose.resources.stringResource

@Composable
fun WcSignPersonalScreen(
    id: WcRequest.Id,
    onCancel: () -> Unit,
    onNavigateUp: () -> Unit,
    onResult: () -> Unit,
) {
    val signPersonal = rememberSignPersonalRequest(id)

    val sign = SignContent(signPersonal.call?.account)

    LaunchedEffect(sign.signedRequest) {
        sign.signedRequest?.let {
            signPersonal.send(it.request as SignRequest.SignPersonal, it.signature)
        }
    }

    LaunchedEffect(signPersonal.sent) {
        if (signPersonal.sent) {
            onResult()
        }
    }

    AppFailureMessage(signPersonal.txFailure) {
        signPersonal.retry()
    }

    WcSignPersonalContent(
        signPersonal = signPersonal,
        signing = sign.signing,
        onSign = { sign.sign(it) },
        onReject = {
            signPersonal.reject()
            onCancel()
        },
        onNavigateUp = onNavigateUp,
    )
}

@Composable
fun WcSignPersonalContent(
    signPersonal: SignPersonalRequestState,
    signing: Boolean,
    onNavigateUp: () -> Unit,
    onSign: (SignRequest.SignPersonal) -> Unit,
    onReject: () -> Unit,
) {
    KoshScaffold(
        title = {
            if (signPersonal.failure == null) {
                DappTitle(signPersonal.request?.dapp)
            }
        },
        onUp = onNavigateUp,

        actions = {
            if (signPersonal.failure == null) {
                DappIcon(signPersonal.request?.dapp)
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
            signPersonal.failure?.let {
                AppFailureItem(it) { signPersonal.retry() }
            } ?: run {
                signPersonal.request?.let {
                    VerifyContextItem(it.verifyContext)
                }

                val account = signPersonal.call?.let { rememberAccount(it.account) }

                AccountItem(account?.entity)

                PersonalMessageCard(signPersonal.call?.message?.value)

                PrimaryButtons(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    cancel = {
                        TextButton(onReject.single()) {
                            Text(stringResource(Res.string.wc_request_reject_btn))
                        }
                    },
                    confirm = {
                        LoadingButton(signing, onClick = {
                            nullable {
                                onSign(
                                    SignRequest.SignPersonal(
                                        account = ensureNotNull(signPersonal.call?.account),
                                        message = ensureNotNull(signPersonal.call?.message),
                                        dapp = ensureNotNull(signPersonal.request?.dapp),
                                        chainId = null,
                                    )
                                )
                            }
                        }) {
                            Text(stringResource(Res.string.wc_request_sign_btn))
                        }
                    }
                )
            }
        }

        LoadingIndicator(signPersonal.loading)
    }
}
