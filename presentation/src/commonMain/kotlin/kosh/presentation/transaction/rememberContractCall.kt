package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.models.web3.ContractCall
import kosh.domain.serializers.BigInteger
import kosh.domain.usecases.transaction.ContractCallService
import kosh.presentation.core.di
import kosh.presentation.rememberLoad

@Composable
fun rememberContractCall(
    chainId: ChainId,
    from: Address,
    to: Address?,
    value: BigInteger,
    data: ByteString,
    callDataParserService: ContractCallService = di { domain.callDataParserService },
): ContractCallState {
    val parsed = rememberLoad(chainId, from, to, data) {
        callDataParserService.parse(chainId, from, to, value, data).bind()
    }

    return remember {
        object : ContractCallState {
            override val contractCall: ContractCall? get() = parsed.result
            override val loading: Boolean get() = parsed.loading
            override val failure: Web3Failure? get() = parsed.failure

            override fun retry() {
                parsed.retry()
            }
        }
    }
}

@Stable
interface ContractCallState {
    val contractCall: ContractCall?
    val loading: Boolean
    val failure: Web3Failure?
    fun retry()
}
