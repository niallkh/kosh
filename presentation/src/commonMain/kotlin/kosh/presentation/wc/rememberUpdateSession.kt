package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcSession
import kosh.domain.usecases.reown.WcSessionService
import kosh.presentation.core.di
import kosh.presentation.rememberEitherEffect

@Composable
fun rememberUpdateSession(
    id: WcSession.Id,
    onUpdated: () -> Unit,
    sessionService: WcSessionService = di { domain.wcSessionService },
): UpdateSessionState {
    val update = rememberEitherEffect(
        id,
        onFinish = { onUpdated() }
    ) { (accounts, chains): Pair<List<AccountEntity>, List<NetworkEntity>> ->
        sessionService.update(id, accounts, chains).bind()
    }

    return remember {
        object : UpdateSessionState {
            override val updating: Boolean get() = update.inProgress
            override val failure: WcFailure? get() = update.failure
            override fun invoke(accounts: List<AccountEntity>, chains: List<NetworkEntity>) {
                update(accounts to chains)
            }
        }
    }
}

@Stable
interface UpdateSessionState {
    val updating: Boolean
    val failure: WcFailure?
    operator fun invoke(accounts: List<AccountEntity>, chains: List<NetworkEntity>)
}
