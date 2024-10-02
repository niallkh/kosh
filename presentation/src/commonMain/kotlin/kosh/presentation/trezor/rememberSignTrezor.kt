package kosh.presentation.trezor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.raise.recover
import kosh.domain.failure.TrezorFailure
import kosh.domain.models.hw.Trezor
import kosh.domain.repositories.TrezorListener
import kosh.domain.usecases.trezor.TrezorAccountService
import kosh.presentation.di.di
import kosh.presentation.models.SignRequest
import kosh.presentation.models.SignedRequest

@Composable
fun rememberSignTrezor(
    trezorListener: TrezorListener,
    trezorAccountService: TrezorAccountService = di { domain.trezorAccountService },
): SignTrezorState {
    var signRequest by remember { mutableStateOf<SignRequest?>(null) }
    var signedRequest by remember { mutableStateOf<SignedRequest?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<TrezorFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    var refresh by remember { mutableStateOf(false) }
    var trezor by remember { mutableStateOf<Trezor?>(null) }

    LaunchedEffect(retry, trezor, signRequest) {
        loading = true

        recover({
            val request = signRequest ?: raise(null)
            val signature = when (request) {
                is SignRequest.SignPersonal -> trezorAccountService.sign(
                    trezor = trezor ?: raise(TrezorFailure.NotConnected()),
                    listener = trezorListener,
                    address = request.account,
                    message = request.message,
                    refresh = refresh
                )

                is SignRequest.SignTyped -> trezorAccountService.sign(
                    listener = trezorListener,
                    trezor = trezor ?: raise(TrezorFailure.NotConnected()),
                    address = request.account,
                    jsonTypeData = request.json,
                    refresh = refresh
                )

                is SignRequest.SignTransaction -> trezorAccountService.sign(
                    listener = trezorListener,
                    trezor = trezor ?: raise(TrezorFailure.NotConnected()),
                    transaction = request.data,
                    refresh = refresh,
                )
            }.bind()

            signedRequest = SignedRequest(request, signature)

            loading = false
            failure = null
        }) {
            loading = false
            failure = it
        }
    }

    return SignTrezorState(
        signedRequest = signedRequest,
        loading = loading,
        failure = failure,
        sign = { trezor1, request ->
            retry++
            signRequest = request
            trezor = trezor1
        },
        retry = {
            retry++
            refresh = true
        }
    )
}

@Immutable
data class SignTrezorState(
    val signedRequest: SignedRequest?,
    val loading: Boolean,
    val failure: TrezorFailure?,

    val sign: (Trezor, SignRequest) -> Unit,
    val retry: () -> Unit,
)
