package kosh.presentation.keystone

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import arrow.core.raise.either
import kosh.domain.failure.KeystoneFailure
import kosh.domain.models.hw.Keystone
import kosh.domain.repositories.KeystoneListener
import kosh.domain.usecases.keystone.KeystoneAccountService
import kosh.presentation.core.di
import kosh.presentation.di.rememberRetained
import kosh.presentation.models.SignRequest
import kosh.presentation.models.SignedRequest
import kosh.presentation.rememberEffect

@Composable
fun rememberSignKeystone(
    keystoneListener: KeystoneListener,
    keystoneAccountService: KeystoneAccountService = di { domain.keystoneAccountService },
): SignKeystoneState {
    var keystone by rememberRetained { mutableStateOf<Keystone?>(null) }
    var signRequest by rememberRetained { mutableStateOf<SignRequest?>(null) }

    val sign = rememberEffect(keystone, signRequest) {
        either {
            val request = signRequest ?: raise(null)
            val keystone1 = keystone ?: raise(null)

            val signature = when (request) {
                is SignRequest.SignPersonal -> keystoneAccountService.sign(
                    listener = keystoneListener,
                    keystone = keystone1,
                    address = request.account,
                    message = request.message,
                )

                is SignRequest.SignTyped -> keystoneAccountService.sign(
                    listener = keystoneListener,
                    keystone = keystone1,
                    address = request.account,
                    jsonTypeData = request.json,
                )

                is SignRequest.SignTransaction -> keystoneAccountService.sign(
                    listener = keystoneListener,
                    keystone = keystone1,
                    transaction = request.data,
                )
            }.bind()

            SignedRequest(request, signature)
        }
    }

    return SignKeystoneState(
        signedRequest = sign.result?.getOrNull(),
        loading = sign.inProgress,
        failure = sign.result?.leftOrNull(),
        sign = { keystone1, request ->
            signRequest = request
            keystone = keystone1
            sign()
        },
        retry = {
            sign()
        }
    )
}


@Immutable
data class SignKeystoneState(
    val signedRequest: SignedRequest?,
    val loading: Boolean,
    val failure: KeystoneFailure?,

    val sign: (Keystone, SignRequest) -> Unit,
    val retry: () -> Unit,
)
