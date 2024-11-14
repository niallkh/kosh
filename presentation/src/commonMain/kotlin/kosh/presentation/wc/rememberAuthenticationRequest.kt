package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.usecases.reown.WcAuthenticationService
import kosh.presentation.core.di
import kosh.presentation.rememberLoad

@Composable
fun rememberAuthenticationRequest(
    id: WcAuthentication.Id,
    account: AccountEntity?,
    network: NetworkEntity?,
    authenticationService: WcAuthenticationService = di { domain.wcAuthenticationService },
): AuthenticationRequestState {
    val authentication = rememberLoad(id, account, network) {
        authenticationService.get(id).bind()
    }

    val message = rememberLoad(id, account, network) {
        authenticationService.getAuthenticationMessage(
            id = id,
            account = account?.address ?: raise(null),
            chainId = network?.chainId ?: raise(null)
        ).bind()
    }

    return remember(id, account, network) {
        object : AuthenticationRequestState {
            override val auth: WcAuthentication? get() = authentication.result
            override val message: WcAuthentication.Message? get() = message.result
            override val loading: Boolean by derivedStateOf {
                authentication.loading || message.loading
            }
            override val failure: WcFailure? by derivedStateOf {
                authentication.failure ?: message.failure
            }

            override fun retry() {
                authentication.retry()
                message.retry()
            }
        }
    }
}

@Stable
interface AuthenticationRequestState {
    val auth: WcAuthentication?
    val message: WcAuthentication.Message?
    val loading: Boolean
    val failure: WcFailure?
    fun retry()
}
