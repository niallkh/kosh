package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.AppFailure
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.web3.Signature
import kosh.domain.usecases.reown.WcAuthenticationService
import kosh.presentation.core.di
import kosh.presentation.models.SignRequest
import kosh.presentation.rememberEitherEffect
import kosh.presentation.transaction.rememberAddSignPersonal

@Composable
fun rememberApproveAuthentication(
    id: WcAuthentication.Id,
    account: AccountEntity?,
    network: NetworkEntity?,
    onApproved: () -> Unit = {},
    authenticationService: WcAuthenticationService = di { domain.wcAuthenticationService },
): ApproveAuthenticationState {
    val approveAuthentication = rememberEitherEffect(
        id, account, network,
        onFinish = { onApproved() }
    ) { signature: Signature ->
        authenticationService.approve(
            id = id,
            account = account?.address ?: raise(null),
            chainId = network?.chainId ?: raise(null),
            signature = signature
        ).bind()
    }

    val addAuthentication = rememberAddSignPersonal(
        onAdded = { approveAuthentication(it) }
    )

    return remember {
        object : ApproveAuthenticationState {
            override val approving: Boolean by derivedStateOf {
                addAuthentication.adding || approveAuthentication.inProgress
            }

            override val failure: AppFailure? by derivedStateOf {
                addAuthentication.failure ?: approveAuthentication.failure
            }

            override fun send(signRequest: SignRequest.SignPersonal, signature: Signature) {
                addAuthentication(signRequest, signature)
            }
        }
    }
}

interface ApproveAuthenticationState {
    val approving: Boolean
    val failure: AppFailure?
    fun send(signRequest: SignRequest.SignPersonal, signature: Signature)
}
