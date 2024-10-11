package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.models.web3.ContractCall
import kosh.domain.serializers.BigInteger
import kosh.domain.usecases.transaction.ContractCallService
import kosh.presentation.Load
import kosh.presentation.core.di

@Composable
fun rememberContractCall(
    chainId: ChainId,
    from: Address,
    to: Address?,
    value: BigInteger,
    data: ByteString,
    callDataParserService: ContractCallService = di { domain.callDataParserService },
): ContractCallState {

    val content = Load(chainId, from, to, data) {
        callDataParserService.parse(chainId, from, to, value, data).bind()
    }

    return ContractCallState(
        contractCall = content.content,
        loading = content.loading,
        failure = content.failure,
        retry = { content.retry() },
    )
}

data class ContractCallState(
    val contractCall: ContractCall?,
    val loading: Boolean,
    val failure: Web3Failure?,
    val retry: () -> Unit,
)
