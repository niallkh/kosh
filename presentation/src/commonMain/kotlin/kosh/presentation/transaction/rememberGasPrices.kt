package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.raise.recover
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.models.ChainId
import kosh.domain.models.web3.GasPrices
import kosh.domain.repositories.GasRepo
import kosh.presentation.core.di
import kosh.presentation.di.rememberLifecycleState
import kosh.presentation.di.rememberSerializable
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun rememberGasPrices(
    chainId: ChainId,
    active: Boolean = true,
) = rememberGasPrices(NetworkEntity.Id(chainId), active)

@Composable
fun rememberGasPrices(
    networkId: NetworkEntity.Id,
    active: Boolean = true,
    gasRepo: GasRepo = di { appRepositoriesComponent.gasRepo },
): GasPricesState {
    var gasPrices by rememberSerializable { mutableStateOf<GasPrices?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<Web3Failure?>(null) }
    var retry by remember { mutableIntStateOf(0) }

    if (rememberLifecycleState()) {
        LaunchedEffect(retry, networkId, active) {
            if (!active) return@LaunchedEffect

            while (true) {
                loading = true

                recover({
                    gasPrices = gasRepo.gasPrices(networkId).bind()

                    loading = false
                    failure = null
                }) {
                    failure = it
                    loading = false
                    gasPrices = null
                }

                delay(30.seconds)
            }
        }
    }

    return GasPricesState(
        prices = gasPrices,
        failure = failure,
        loading = loading,
        retry = { retry++ }
    )
}

@Immutable
data class GasPricesState(
    val prices: GasPrices?,
    val loading: Boolean,
    val failure: Web3Failure?,
    val retry: () -> Unit,
)

