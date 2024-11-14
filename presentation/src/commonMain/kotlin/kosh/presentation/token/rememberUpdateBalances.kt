package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kosh.domain.failure.Web3Failure
import kosh.domain.serializers.Nel
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.balancesKey
import kosh.domain.usecases.token.TokenBalanceService
import kosh.domain.utils.optic
import kosh.presentation.component.selector.selector
import kosh.presentation.core.di
import kosh.presentation.di.rememberLifecycleState
import kosh.presentation.ticker.rememberTimer
import kosh.presentation.ticker.runAtLeast
import kotlinx.serialization.builtins.serializer
import kotlin.time.Duration.Companion.minutes

@Composable
fun rememberUpdateBalances(
    tokenBalanceService: TokenBalanceService = di { domain.tokenBalanceService },
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): UpdateBalancesState {
    var loading by remember { mutableStateOf(false) }
    var refreshing by remember { mutableStateOf(false) }
    var failures by remember { mutableStateOf<Nel<Web3Failure>?>(null) }
    val timer = rememberTimer(5.minutes)

    if (appStateProvider.init && rememberLifecycleState()) {
        LaunchedEffect(Unit) {
            while (true) {
                timer.waitNext()

                loading = true

                failures = runAtLeast { tokenBalanceService.update() }.leftOrNull()

                loading = false
                refreshing = false
            }
        }
    }

    optic(AppState.balancesKey()) { appStateProvider.state }.selector(String.serializer()) {
        timer.reset()
    }

    return UpdateBalancesState(
        loading = loading,
        refreshing = refreshing,
        failures = failures,
        refresh = {
            refreshing = true
            timer.reset()
        },
    )
}

@Immutable
data class UpdateBalancesState(
    val loading: Boolean,
    val refreshing: Boolean,
    val failures: Nel<Web3Failure>?,
    val refresh: () -> Unit,
)
