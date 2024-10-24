package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kosh.domain.models.token.TokenBalance
import kosh.domain.serializers.ImmutableList
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.balances
import kosh.domain.utils.optic
import kosh.presentation.core.di

@Composable
fun rememberBalances(
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): BalancesState {
    val balances by appStateProvider.collectAsState().optic(AppState.balances())

    return BalancesState(
        balances = balances,
        init = appStateProvider.init
    )
}

@Immutable
data class BalancesState(
    val balances: ImmutableList<TokenBalance>,
    val init: Boolean,
)
