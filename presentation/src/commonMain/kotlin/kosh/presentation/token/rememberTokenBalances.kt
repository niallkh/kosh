package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kosh.domain.entities.TokenEntity
import kosh.domain.models.token.AccountBalance
import kosh.domain.usecases.token.TokenBalanceService
import kosh.presentation.di.di
import kosh.presentation.di.rememberLifecycleState
import kosh.presentation.di.rememberSerializable
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun rememberTokenBalances(
    id: TokenEntity.Id,
    tokenBalanceService: TokenBalanceService = di { domain.tokenBalanceService },
): TokenBalancesState {
    var balances by rememberSerializable { mutableStateOf<List<AccountBalance>>(persistentListOf()) }
    var loading by remember { mutableStateOf(false) }

    if (rememberLifecycleState()) {
        LaunchedEffect(id) {
            loading = true
            tokenBalanceService.getTokenBalances(id).collect {
                loading = false
                balances = it.toImmutableList()
            }
        }
    }

    return TokenBalancesState(
        balances = balances,
        loading = loading,
    )
}

@Immutable
data class TokenBalancesState(
    val balances: List<AccountBalance>,
    val loading: Boolean,
)
