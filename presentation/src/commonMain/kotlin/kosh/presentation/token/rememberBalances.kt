package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.getOrElse
import arrow.core.raise.recover
import kosh.domain.failure.Web3Failure
import kosh.domain.models.token.TokenBalance
import kosh.domain.serializers.ImmutableList
import kosh.domain.serializers.Nel
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.balances
import kosh.domain.state.balancesKey
import kosh.domain.usecases.token.TokenBalanceService
import kosh.domain.utils.optic
import kosh.presentation.component.selector.selector
import kosh.presentation.di.di
import kosh.presentation.di.rememberLifecycleState
import kosh.presentation.ticker.rememberTicker
import kotlin.time.Duration.Companion.minutes

@Composable
fun rememberBalances(
    tokenBalanceService: TokenBalanceService = di { domain.tokenBalanceService },
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): BalancesState {
    val balances by appStateProvider.collectAsState().optic(AppState.balances())
    var loading by remember { mutableStateOf(false) }
    var failures by remember { mutableStateOf<Nel<Web3Failure>?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    val ticker = rememberTicker(5.minutes)

    if (rememberLifecycleState()) {
        LaunchedEffect(retry) {
            while (true) {
                ticker.waitNext()

                loading = true

                recover({
                    with(tokenBalanceService) {
                        update()
                    }.getOrElse {
                        raise(it)
                    }

                    loading = false
                    failures = null
                }) {
                    loading = false
                    failures = it
                }
            }
        }
    }

    appStateProvider.collectAsState().optic(AppState.balancesKey()).selector {
        ticker.reset()
    }

    return BalancesState(
        balances = balances,
        loading = loading,
        failures = failures,
        retry = {
            retry++
            ticker.reset()
        }
    )
}

@Immutable
data class BalancesState(
    val balances: ImmutableList<TokenBalance>,
    val loading: Boolean,
    val failures: Nel<Web3Failure>?,
    val retry: () -> Unit,
)

