package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.Web3Failure
import kosh.domain.models.web3.GasEstimation
import kosh.domain.models.web3.Transaction
import kosh.domain.repositories.GasRepo
import kosh.presentation.core.di
import kosh.presentation.rememberLoad

@Composable
fun rememberEstimateGas(
    transaction: Transaction?,
    gasRepo: GasRepo = di { appRepositoriesComponent.gasRepo },
): EstimateGasState {
    val gasEstimation = rememberLoad(transaction) {
        transaction ?: raise(null)

        with(transaction) {
            gasRepo.estimate(chainId, from, to, value, input, gas).bind()
        }
    }

    return remember {
        object : EstimateGasState {
            override val estimation: GasEstimation?
                get() = gasEstimation.result
            override val loading: Boolean
                get() = gasEstimation.loading
            override val failure: Web3Failure?
                get() = gasEstimation.failure

            override fun retry() {
                gasEstimation.retry()
            }
        }
    }
}

@Stable
interface EstimateGasState {
    val estimation: GasEstimation?
    val loading: Boolean
    val failure: Web3Failure?
    fun retry()
}
