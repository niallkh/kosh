package kosh.presentation.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import arrow.core.Nel
import kosh.domain.entities.AccountEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.usecases.account.AccountTokensDiscoveryService
import kosh.presentation.core.di
import kosh.presentation.rememberIorEffect

@Composable
fun rememberDiscoverAccountTokens(
    id: AccountEntity.Id,
    onFinish: () -> Unit,
    accountTokensDiscoveryService: AccountTokensDiscoveryService = di { domain.accountTokensDiscoveryService },
): DiscoverAccountTokensState {
    val discovery = rememberIorEffect(id, onFinish = { onFinish() }) { _: Unit ->
        accountTokensDiscoveryService.discoverTokens(id)
    }

    return remember {
        object : DiscoverAccountTokensState {
            override val discovering: Boolean get() = discovery.inProgress
            override val errors: Nel<Web3Failure>? get() = discovery.failure?.takeIf { !discovery.both }
            override fun discover() = discovery(Unit)
        }
    }
}

interface DiscoverAccountTokensState {
    val discovering: Boolean
    val errors: Nel<Web3Failure>?
    fun discover()
}
