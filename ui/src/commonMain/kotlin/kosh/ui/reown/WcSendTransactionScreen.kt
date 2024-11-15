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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.raise.nullable
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.web3.ContractCall
import kosh.domain.models.web3.TransactionData
import kosh.presentation.account.AccountState
import kosh.presentation.account.rememberAccount
import kosh.presentation.models.SignRequest
import kosh.presentation.network.NetworkState
import kosh.presentation.network.rememberNetwork
import kosh.presentation.transaction.ContractCallState
import kosh.presentation.transaction.EstimateGasState
import kosh.presentation.transaction.GasPricesState
import kosh.presentation.transaction.GasSpeedState
import kosh.presentation.transaction.NextNonceState
import kosh.presentation.transaction.get
import kosh.presentation.transaction.rememberContractCall
import kosh.presentation.transaction.rememberEstimateGas
import kosh.presentation.transaction.rememberGasPrices
import kosh.presentation.transaction.rememberGasSpeed
import kosh.presentation.transaction.rememberNextNonce
import kosh.presentation.wc.RejectRequestState
import kosh.presentation.wc.SendTransactionRequestState
import kosh.presentation.wc.WcSendTransactionState
import kosh.presentation.wc.rememberRejectRequest
import kosh.presentation.wc.rememberSendTransactionRequest
import kosh.presentation.wc.rememberWcSendTransaction
import kosh.ui.component.LoadingIndicator
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
    request: SendTransactionRequestState = rememberSendTransactionRequest(id),
    sendTransaction: WcSendTransactionState = rememberWcSendTransaction(id, onFinish),
    account: AccountState? = request.transaction?.let { rememberAccount(it.from) },
    network: NetworkState? = request.transaction?.let { rememberNetwork(it.chainId) },
    rejectRequest: RejectRequestState = rememberRejectRequest(id, onCancel),
    gasSpeed: GasSpeedState = rememberGasSpeed(),
    gasPrices: GasPricesState = rememberGasPrices(request.transaction?.chainId),
    estimateGas: EstimateGasState = rememberEstimateGas(request.transaction),
    nextNonce: NextNonceState = rememberNextNonce(
        request.transaction?.chainId,
        request.transaction?.from
    ),
    contractCall: ContractCallState? = request.transaction?.let {
        rememberContractCall(it.chainId, it.from, it.to, it.value, it.input)
    },
) {
    KoshScaffold(
        title = {
            if (request.failure == null) {
                DappTitle(request.request?.dapp)
            }
        },
        onNavigateUp = onNavigateUp,
        actions = {
            if (request.failure == null) {
                DappIcon(request.request?.dapp)
            }
            Spacer(Modifier.width(8.dp))
        }
    ) { paddingValues ->

        val sign = SignContent(account?.entity?.walletId) {
            sendTransaction(it.request as SignRequest.SignTransaction, it.signature)
        }

        AppFailureMessage(sign.failure)
        AppFailureMessage(rejectRequest.failure)
        AppFailureMessage(sendTransaction.failure)

        val signing by remember {
            derivedStateOf { sign.signing || sendTransaction.sending }
        }

        WcSendTransactionContent(
            request = request,
            gasPrices = gasPrices,
            nextNonce = nextNonce,
            estimateGas = estimateGas,
            gasSpeed = gasSpeed,
            signing = signing,
            account = account,
            network = network,
            contractCall = contractCall,
            rejecting = rejectRequest.rejecting,
            onReject = rejectRequest::invoke,
            onSign = {
                nullable {
                    val gasPrice = gasPrices.prices?.get(gasSpeed.speed)

                    val data = TransactionData(
                        tx = ensureNotNull(request.transaction),
                        gasPrice = ensureNotNull(gasPrice),
                        gasLimit = ensureNotNull(estimateGas.estimation?.gas),
                        nonce = ensureNotNull(nextNonce.nonce),
                    )

                    val signTransaction = SignRequest.SignTransaction(
                        data = data,
                        dapp = ensureNotNull(request.request?.dapp),
                    )

                    sign(signTransaction)
                }
            },
            onOpen = onOpen,
            contentPadding = paddingValues,
        )

        LoadingIndicator(
            request.loading,
            Modifier.padding(paddingValues),
        )
    }
}

@Composable
fun WcSendTransactionContent(
    request: SendTransactionRequestState,
    gasSpeed: GasSpeedState,
    gasPrices: GasPricesState,
    estimateGas: EstimateGasState,
    nextNonce: NextNonceState,
    account: AccountState?,
    network: NetworkState?,
    contractCall: ContractCallState?,
    signing: Boolean,
    rejecting: Boolean,
    onSign: () -> Unit,
    onReject: () -> Unit,
    onOpen: (RootRoute) -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(contentPadding),
    ) {
        request.failure?.let {
            AppFailureItem(it) { request.retry() }
        } ?: run {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                request.request?.let {
                    VerifyContextItem(it.verifyContext)
                }

                Column {
                    NetworkItem(network?.entity)

                    AccountItem(account?.entity) {
                        TextNonce(nextNonce)
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TransactionCall(
                        contractCall = contractCall,
                        onOpen = onOpen,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    NetworkFeesCard(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        transaction = request.transaction,
                        gasSpeed = gasSpeed,
                        gasPrices = gasPrices,
                        gasEstimation = estimateGas,
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
                                Text(stringResource(Res.string.wc_sign_tx_send_btn))
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
private fun TextNonce(
    nextNonce: NextNonceState,
) {
    AppFailureMessage(nextNonce.failure) { nextNonce.retry() }

    Row {
        Text("nonce: ")

        nextNonce.failure?.let {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        } ?: TextNumber(
            nextNonce.nonce ?: 0u,
            Modifier.placeholder(nextNonce.nonce == null)
        )
    }
}

@Composable
fun TransactionCall(
    contractCall: ContractCallState?,
    onOpen: (RootRoute) -> Unit,
    modifier: Modifier = Modifier,
) {
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
