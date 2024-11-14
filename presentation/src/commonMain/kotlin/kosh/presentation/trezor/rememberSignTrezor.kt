package kosh.presentation.trezor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import kosh.domain.failure.TrezorFailure
import kosh.domain.models.hw.Trezor
import kosh.domain.repositories.TrezorListener
import kosh.domain.usecases.trezor.TrezorAccountService
import kosh.presentation.core.di
import kosh.presentation.models.SignRequest
import kosh.presentation.models.SignedRequest
import kosh.presentation.rememberEitherEffect

@Composable
fun rememberSignTrezor(
    trezorListener: TrezorListener,
    onSigned: (SignedRequest) -> Unit = {},
    trezorAccountService: TrezorAccountService = di { domain.trezorAccountService },
): SignTrezorState {
    val signTrezor = rememberEitherEffect(
        trezorListener,
        onFinish = onSigned
    ) { (trezor, request, refresh): Triple<Trezor, SignRequest, Boolean> ->
        val signature = when (request) {
            is SignRequest.SignPersonal -> trezorAccountService.sign(
                trezor = trezor,
                listener = trezorListener,
                address = request.account,
                message = request.message,
                refresh = refresh,
            )

            is SignRequest.SignTyped -> trezorAccountService.sign(
                listener = trezorListener,
                trezor = trezor,
                address = request.account,
                jsonTypeData = request.json,
                refresh = refresh
            )

            is SignRequest.SignTransaction -> trezorAccountService.sign(
                listener = trezorListener,
                trezor = trezor,
                transaction = request.data,
                refresh = refresh,
            )
        }.bind()

        SignedRequest(request, signature)
    }

    return remember {
        object : SignTrezorState {
            override val signedRequest: SignedRequest? get() = signTrezor.result
            override val loading: Boolean get() = signTrezor.inProgress
            override val failure: TrezorFailure? = signTrezor.failure
            override operator fun invoke(trezor: Trezor, request: SignRequest) {
                signTrezor(Triple(trezor, request, false))
            }
        }
    }
}

@Immutable
interface SignTrezorState {
    val signedRequest: SignedRequest?
    val loading: Boolean
    val failure: TrezorFailure?

    operator fun invoke(trezor: Trezor, request: SignRequest)
}
