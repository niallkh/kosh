package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.repositories.TransactionRepo
import kosh.presentation.core.di
import kosh.presentation.rememberLoad

@Composable
fun rememberNextNonce(
    chainId: ChainId?,
    account: Address?,
    transactionRepo: TransactionRepo = di { appRepositoriesComponent.transactionRepo },
): NextNonceState {
    val nonce = rememberLoad(chainId, account) {
        transactionRepo.nextNonce(
            chainId = chainId ?: raise(null),
            address = account ?: raise(null),
        ).bind()
    }

    return remember {
        object : NextNonceState {
            override val nonce: ULong? get() = nonce.result
            override val loading: Boolean get() = nonce.loading
            override val failure: Web3Failure? get() = nonce.failure
            override fun retry() {
                nonce.retry()
            }
        }
    }
}

@Stable
interface NextNonceState {
    val nonce: ULong?
    val loading: Boolean
    val failure: Web3Failure?
    fun retry()
}
