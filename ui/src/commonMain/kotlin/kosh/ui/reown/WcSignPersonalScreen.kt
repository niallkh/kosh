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
import kosh.presentation.wc.RejectRequestState
import kosh.presentation.wc.SendPersonalMessageState
import kosh.presentation.wc.SignPersonalRequestState
import kosh.presentation.wc.rememberRejectRequest
import kosh.presentation.wc.rememberSendPersonalMessage
import kosh.presentation.wc.rememberSignPersonalRequest
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.button.LoadingButton
import kosh.ui.component.button.LoadingTextButton
import kosh.ui.component.button.PrimaryButtons
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.dapp.DappTitle
import kosh.ui.component.items.AccountItem
import kosh.ui.component.items.VerifyContextItem
import kosh.ui.component.scaffold.KoshScaffold
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
    onFinish: () -> Unit,
    signPersonal: SignPersonalRequestState = rememberSignPersonalRequest(id),
    account: AccountState? = signPersonal.call?.let { rememberAccount(it.account) },
    sendPersonalMessage: SendPersonalMessageState = rememberSendPersonalMessage(id, onFinish),
    rejectRequest: RejectRequestState = rememberRejectRequest(id, onCancel),
) {
    KoshScaffold(
        title = {
            if (signPersonal.failure == null) {
                DappTitle(signPersonal.request?.dapp)
            }
        },
        onNavigateUp = onNavigateUp,
        actions = {
            if (signPersonal.failure == null) {
                DappIcon(signPersonal.request?.dapp)
            }
            Spacer(Modifier.width(8.dp))
        }
    ) { paddingValues ->
        val sign = SignContent(account?.entity?.walletId) {
            sendPersonalMessage.send(it.request as SignRequest.SignPersonal, it.signature)
        }

        AppFailureMessage(rejectRequest.failure)

        AppFailureMessage(sign.failure)

        AppFailureMessage(sendPersonalMessage.failure)

        val signing by remember {
            derivedStateOf { sign.signing || sendPersonalMessage.sending }
        }

        WcSignPersonalContent(
            signPersonal = signPersonal,
            signing = signing,
            account = account,
            rejecting = rejectRequest.rejecting,
            onSign = {
                nullable {
                    val signRequest = SignRequest.SignPersonal(
                        account = ensureNotNull(signPersonal.call?.account),
                        message = ensureNotNull(signPersonal.call?.message),
                        dapp = ensureNotNull(signPersonal.request?.dapp),
                        chainId = null,
                    )

                    sign(signRequest)
                }
            },
            onReject = rejectRequest::invoke,
            contentPadding = paddingValues
        )

        LoadingIndicator(
            signPersonal.loading,
            Modifier.padding(paddingValues),
        )
    }
}

@Composable
fun WcSignPersonalContent(
    signPersonal: SignPersonalRequestState,
    account: AccountState?,
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
        signPersonal.failure?.let {
            AppFailureItem(it) { signPersonal.retry() }
        } ?: run {
            signPersonal.request?.let {
                VerifyContextItem(it.verifyContext)
            }

            AccountItem(account?.entity)

            PersonalMessageCard(signPersonal.call?.message?.value)

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

        Spacer(Modifier.size(128.dp))
    }
}
