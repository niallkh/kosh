package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.repositories.TransactionRepo
import kosh.presentation.LoadContent
import kosh.presentation.di.di

@Composable
fun rememberNextNonce(
    chainId: ChainId,
    account: Address,
    transactionRepo: TransactionRepo = di { appRepositories.transactionRepo },
): NextNonceState {
    val nonce = LoadContent(chainId, account) {
        transactionRepo.nextNonce(chainId, account).bind()
    }

    return NextNonceState(
        nonce = nonce.content,
        loading = nonce.loading,
        failure = nonce.failure,
        retry = { nonce.retry() }
    )
}

data class NextNonceState(
    val nonce: ULong?,
    val loading: Boolean,
    val failure: Web3Failure?,
    val retry: () -> Unit,
)