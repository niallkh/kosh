package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.repositories.TransactionRepo
import kosh.presentation.core.di
import kosh.presentation.rememberLoad

@Composable
fun rememberNextNonce(
    chainId: ChainId,
    account: Address,
    transactionRepo: TransactionRepo = di { appRepositoriesComponent.transactionRepo },
): NextNonceState {
    val nonce = rememberLoad(chainId, account) {
        transactionRepo.nextNonce(chainId, account)
    }

    return NextNonceState(
        nonce = nonce.result?.getOrNull(),
        loading = nonce.loading,
        failure = nonce.result?.leftOrNull(),
        retry = { nonce() }
    )
}

data class NextNonceState(
    val nonce: ULong?,
    val loading: Boolean,
    val failure: Web3Failure?,
    val retry: () -> Unit,
)
