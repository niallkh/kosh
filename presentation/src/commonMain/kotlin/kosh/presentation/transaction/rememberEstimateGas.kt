package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.failure.Web3Failure
import kosh.domain.models.web3.GasEstimation
import kosh.domain.models.web3.Transaction
import kosh.domain.repositories.GasRepo
import kosh.presentation.LoadContent
import kosh.presentation.di.di

@Composable
fun rememberEstimateGas(
    transaction: Transaction,
    gasRepo: GasRepo = di { appRepositories.gasRepo },
): EstimateGasState {
    val gasEstimation = LoadContent {
        with(transaction) {
            gasRepo.estimate(chainId, from, to, value, input, gas).bind()
        }
    }

    return EstimateGasState(
        estimation = gasEstimation.content,
        loading = gasEstimation.loading,
        failure = gasEstimation.failure,
        retry = { gasEstimation.retry() }
    )
}

@Immutable
data class EstimateGasState(
    val estimation: GasEstimation?,
    val loading: Boolean,
    val failure: Web3Failure?,
    val retry: () -> Unit,
)