package kosh.presentation.reown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import arrow.core.raise.nullable
import arrow.core.raise.recover
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.TransactionFailure
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.web3.Signature
import kosh.domain.usecases.reown.WcAuthenticationService
import kosh.presentation.di.di
import kosh.presentation.di.rememberSerializable
import kosh.presentation.models.SignRequest
import kosh.presentation.transaction.rememberSignPersonal

@Composable
fun rememberAuthenticationRequest(
    id: WcAuthentication.Id,
    account: AccountEntity?,
    network: NetworkEntity?,
    authenticationService: WcAuthenticationService = di { domain.wcAuthenticationService },
): AuthenticationRequestState {
    var authentication by rememberSerializable { mutableStateOf<WcAuthentication?>(null) }
    var message by rememberSerializable { mutableStateOf<WcAuthentication.Message?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<WcFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    var sent by remember { mutableStateOf(false) }

    val signPersonal = rememberSignPersonal()

    LaunchedEffect(signPersonal.signature) {
        nullable {
            authenticationService.approve(
                id = id,
                account = account?.address ?: raise(null),
                chainId = network?.chainId ?: raise(null),
                signature = signPersonal.signature ?: raise(null),
            )
            sent = true
        }
    }

    LaunchedEffect(id, retry) {
        if (authentication != null) return@LaunchedEffect
        loading = true

        recover({
            authentication = authenticationService.get(id).bind()

            loading = false
            failure = null
        }) {
            failure = it
            authentication = null
            loading = false
        }
    }

    LaunchedEffect(authentication, retry, account, network) {
        authentication ?: return@LaunchedEffect

        recover({
            message = authenticationService.getAuthenticationMessage(
                id = id,
                account = account?.address ?: raise(null),
                chainId = network?.chainId ?: raise(null)
            ).bind()

            loading = false
            failure = null
        }) {
            failure = it
            authentication = null
            loading = false
        }
    }

    return AuthenticationRequestState(
        auth = authentication,
        message = message,
        sent = sent,
        loading = loading || signPersonal.loading,
        failure = failure,
        txFailure = signPersonal.failure,
        send = { req, signature ->
            sent = false
            signPersonal.add(req, signature)
        },
        reject = { authenticationService.reject(id) },
        retry = {
            if (signPersonal.failure != null) {
                signPersonal.retry()
            } else {
                retry++
            }
        }
    )
}

@Immutable
data class AuthenticationRequestState(
    val auth: WcAuthentication?,
    val message: WcAuthentication.Message?,
    val sent: Boolean,
    val loading: Boolean,
    val failure: WcFailure?,
    val txFailure: TransactionFailure?,
    val send: (SignRequest.SignPersonal, Signature) -> Unit,
    val retry: () -> Unit,
    val reject: () -> Unit,
)
