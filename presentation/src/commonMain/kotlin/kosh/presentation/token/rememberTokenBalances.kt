package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kosh.domain.entities.TokenEntity
import kosh.domain.models.token.AccountBalance
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.tokenBalances
import kosh.domain.utils.optic
import kosh.presentation.core.di

@Composable
fun rememberTokenBalances(
    id: TokenEntity.Id,
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): TokenBalancesState {
    val balances by appStateProvider.collectAsState().optic(AppState.tokenBalances(id))

    return TokenBalancesState(
        balances = balances
    )
}

@Immutable
data class TokenBalancesState(
    val balances: List<AccountBalance>,
)
