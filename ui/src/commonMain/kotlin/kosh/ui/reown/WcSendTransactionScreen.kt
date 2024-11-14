package kosh.ui.reown

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.raise.nullable
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.reown.toTransactionData
import kosh.domain.models.web3.ContractCall
import kosh.domain.models.web3.Transaction
import kosh.domain.models.web3.TransactionData
import kosh.presentation.account.rememberAccount
import kosh.presentation.models.SignRequest
import kosh.presentation.network.rememberNetwork
import kosh.presentation.transaction.get
import kosh.presentation.transaction.rememberContractCall
import kosh.presentation.transaction.rememberEstimateGas
import kosh.presentation.transaction.rememberGasPrices
import kosh.presentation.transaction.rememberGasSpeed
import kosh.presentation.transaction.rememberNextNonce
import kosh.presentation.wc.SendTransactionRequestState
import kosh.presentation.wc.rememberSendTransactionRequest
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.button.LoadingButton
import kosh.ui.component.button.PrimaryButtons
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.dapp.DappTitle
import kosh.ui.component.items.AccountItem
import kosh.ui.component.items.NetworkItem
import kosh.ui.component.items.VerifyContextItem
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.single.single
import kosh.ui.component.text.TextNumber
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.resources.Res
import kosh.ui.resources.wc_request_reject_btn
import kosh.ui.resources.wc_sign_tx_send_btn
import kosh.ui.transaction.NetworkFeesCard
import kosh.ui.transaction.SignContent
import kosh.ui.transaction.calls.ApproveCard
import kosh.ui.transaction.calls.DeployCard
import kosh.ui.transaction.calls.FallbackCard
import kosh.ui.transaction.calls.NativeTransferCard
import kosh.ui.transaction.calls.TransferCard
import org.jetbrains.compose.resources.stringResource

@Composable
fun WcSendTransactionScreen(
    id: WcRequest.Id,
    onCancel: () -> Unit,
    onFinish: () -> Unit,
    onOpen: (RootRoute) -> Unit,
    onNavigateUp: () -> Unit,
) {
    val sendTransaction = rememberSendTransactionRequest(id)

    KoshScaffold(
        title = {
            if (sendTransaction.failure == null) {
                DappTitle(sendTransaction.request?.dapp)
            }
        },
        onNavigateUp = onNavigateUp,

        actions = {
            if (sendTransaction.failure == null) {
                DappIcon(sendTransaction.request?.dapp)
            }
            Spacer(Modifier.width(8.dp))
        }
    ) { paddingValues ->

        val sign = SignContent(TODO())

        LaunchedEffect(sign.signedRequest) {
            sign.signedRequest?.let {
                sendTransaction.send(it.request as SignRequest.SignTransaction, it.signature)
            }
        }

        LaunchedEffect(sendTransaction.sent) {
            if (sendTransaction.sent) {
                onFinish()
            }
        }

        AppFailureMessage(sendTransaction.txFailure) {
            sendTransaction.retry()
        }

        WcSendTransactionContent(
            sendTransaction = sendTransaction,
            signing = sign.signing,
            onReject = {
                sendTransaction.reject()
                onCancel()
            },
            onSign = { sign(it) },
            contentPadding = paddingValues,
            onOpen = onOpen,
        )

        LoadingIndicator(
            sendTransaction.loading,
            Modifier.padding(paddingValues),
        )
    }
}

@Composable
fun WcSendTransactionContent(
    sendTransaction: SendTransactionRequestState,
    signing: Boolean,
    onSign: (SignRequest.SignTransaction) -> Unit,
    onReject: () -> Unit,
    onOpen: (RootRoute) -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(contentPadding),
    ) {
        sendTransaction.failure?.let {
            AppFailureItem(it) { sendTransaction.retry() }
        } ?: run {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val transaction = sendTransaction.call?.toTransactionData()
                val nonce = transaction?.let { rememberNextNonce(it.chainId, it.from) }

                sendTransaction.request?.let {
                    VerifyContextItem(it.verifyContext)
                }

                Column {
                    val network = sendTransaction.call?.chainId?.let { rememberNetwork(it) }

                    NetworkItem(network?.entity)

                    val account = sendTransaction.call?.from?.let { rememberAccount(it) }

                    AccountItem(account?.entity) {
                        Row {
                            Text("nonce: ")
                            nonce?.failure?.let {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                            } ?: TextNumber(
                                nonce?.nonce ?: 0u,
                                Modifier.placeholder(nonce?.nonce == null)
                            )
                            AppFailureMessage(nonce?.failure) { nonce?.retry?.invoke() }
                        }
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TransactionCall(
                        data = transaction,
                        onOpen = onOpen,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    val gasSpeed = rememberGasSpeed()
                    val gasPrices = transaction?.chainId?.let {
                        rememberGasPrices(
                            chainId = it,
                            active = !signing,
                        )
                    }
                    val gasEstimation = transaction?.let { rememberEstimateGas(transaction) }

                    NetworkFeesCard(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        transaction = transaction,
                        gasSpeed = gasSpeed,
                        gasPrices = gasPrices,
                        gasEstimation = gasEstimation,
                    )

                    PrimaryButtons(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        confirm = {
                            LoadingButton(
                                text = { Text(stringResource(Res.string.wc_sign_tx_send_btn)) },
                                loading = signing,
                                onClick = {
                                    nullable {
                                        val gasPrice = gasPrices?.prices?.get(gasSpeed.speed)
                                        val data = TransactionData(
                                            tx = ensureNotNull(transaction),
                                            gasPrice = ensureNotNull(gasPrice),
                                            gasLimit = ensureNotNull(gasEstimation?.estimation?.gas),
                                            nonce = ensureNotNull(nonce?.nonce),
                                        )
                                        val signTransaction = SignRequest.SignTransaction(
                                            data = data,
                                            dapp = ensureNotNull(sendTransaction.request?.dapp),
                                        )
                                        onSign(signTransaction)
                                    }
                                }
                            )
                        },
                        cancel = {
                            TextButton(onReject.single()) {
                                Text(stringResource(Res.string.wc_request_reject_btn))
                            }
                        }
                    )
                }
            }

            Spacer(Modifier.height(128.dp))
        }
    }
}

@Composable
fun TransactionCall(
    data: Transaction?,
    onOpen: (RootRoute) -> Unit,
    modifier: Modifier = Modifier,
) {
    val contractCall = data?.let {
        rememberContractCall(it.chainId, it.from, it.to, it.value, it.input)
    }

    contractCall?.failure?.let {
        AppFailureItem(it) { contractCall.retry() }
    } ?: run {

        when (val call = contractCall?.contractCall) {
            is ContractCall.Transfer -> TransferCard(call, onOpen, modifier)
            is ContractCall.Approve -> ApproveCard(call, onOpen, modifier)
            is ContractCall.Deploy -> DeployCard(call)
            is ContractCall.NativeTransfer -> NativeTransferCard(call, modifier)
            is ContractCall.Fallback -> FallbackCard(call, modifier)
            null -> FallbackCard(call, modifier)
        }
    }
}
