package kosh.presentation.keystone

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.KeystoneFailure
import kosh.domain.models.hw.Keystone
import kosh.domain.repositories.KeystoneListener
import kosh.domain.usecases.keystone.KeystoneAccountService
import kosh.presentation.core.di
import kosh.presentation.models.SignRequest
import kosh.presentation.models.SignedRequest
import kosh.presentation.rememberEitherEffect

@Composable
fun rememberSignKeystone(
    keystoneListener: KeystoneListener,
    onSigned: (SignedRequest) -> Unit = {},
    keystoneAccountService: KeystoneAccountService = di { domain.keystoneAccountService },
): SignKeystoneState {

    val signKeystone = rememberEitherEffect(
        keystoneListener,
        onFinish = onSigned
    ) { (keystone, request): Pair<Keystone, SignRequest> ->
        val signature = when (request) {
            is SignRequest.SignPersonal -> keystoneAccountService.sign(
                listener = keystoneListener,
                keystone = keystone,
                address = request.account,
                message = request.message,
            )

            is SignRequest.SignTyped -> keystoneAccountService.sign(
                listener = keystoneListener,
                keystone = keystone,
                address = request.account,
                jsonTypeData = request.json,
            )

            is SignRequest.SignTransaction -> keystoneAccountService.sign(
                listener = keystoneListener,
                keystone = keystone,
                transaction = request.data,
            )
        }.bind()

        SignedRequest(request, signature)
    }

    return remember {
        object : SignKeystoneState {
            override val signedRequest: SignedRequest?
                get() = signKeystone.result
            override val loading: Boolean
                get() = signKeystone.inProgress
            override val failure: KeystoneFailure?
                get() = signKeystone.failure

            override fun sign(keystone: Keystone, request: SignRequest) {
                signKeystone(Pair(keystone, request))
            }
        }
    }
}

@Stable
interface SignKeystoneState {
    val signedRequest: SignedRequest?
    val loading: Boolean
    val failure: KeystoneFailure?

    fun sign(keystone: Keystone, request: SignRequest)
}
