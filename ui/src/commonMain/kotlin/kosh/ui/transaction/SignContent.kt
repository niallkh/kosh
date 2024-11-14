package kosh.ui.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.node.Ref
import com.arkivanov.decompose.router.slot.dismiss
import kosh.domain.entities.WalletEntity
import kosh.domain.failure.AppFailure
import kosh.presentation.di.rememberRetained
import kosh.presentation.keystone.rememberKeystoneListener
import kosh.presentation.keystore.rememberKeyStoreListener
import kosh.presentation.ledger.rememberLedgerListener
import kosh.presentation.models.SignRequest
import kosh.presentation.models.SignedRequest
import kosh.presentation.transaction.rememberSign
import kosh.presentation.trezor.rememberTrezorListener
import kosh.ui.keystore.KeyStoreListenerContent
import kosh.ui.ledger.LedgerButtonRequest
import kosh.ui.navigation.slot.Slot
import kosh.ui.navigation.slot.SlotHost
import kosh.ui.navigation.slot.activate
import kosh.ui.navigation.slot.rememberSlotRouter
import kosh.ui.trezor.TrezorListenerContent
import kosh.ui.wallet.HardwareWalletSelector

@Composable
fun SignContent(
    walletId: WalletEntity.Id?,
    onSigned: (SignedRequest) -> Unit = {},
): SignContentState {
    val keyStoreListener = rememberKeyStoreListener()

    KeyStoreListenerContent(
        keyStoreListener = keyStoreListener
    )

    val trezorListener = rememberTrezorListener(walletId, keyStoreListener.listener)

    TrezorListenerContent(trezorListener)

    val ledgerListener = rememberLedgerListener()

    val keystoneListener = rememberKeystoneListener()

    LedgerButtonRequest(
        request = ledgerListener.buttonRequest,
        onConfirm = ledgerListener.confirm,
        onDismiss = ledgerListener.dismiss
    )

    val sign = rememberSign(
        trezorListener = trezorListener.listener,
        ledgerListener = ledgerListener.listener,
        keystoneListener = keystoneListener.listener,
        onSigned = onSigned,
    )

    val slotRouter = rememberSlotRouter<Slot>()
    val signRequest = rememberRetained { Ref<SignRequest>() }

    SlotHost(slotRouter) {
        HardwareWalletSelector(
            onDismiss = slotRouter::dismiss,
            onSelected = { hw -> signRequest.value?.let { sign(hw, it) } }
        )
    }

    return remember {
        object : SignContentState {
            override val signedRequest: SignedRequest?
                get() = sign.signedRequest
            override val signing: Boolean
                get() = sign.signing
            override val failure: AppFailure?
                get() = sign.failure

            override operator fun invoke(request: SignRequest) {
                signRequest.value = request
                slotRouter.activate()
            }
        }
    }
}

@Stable
interface SignContentState {
    val signedRequest: SignedRequest?
    val signing: Boolean
    val failure: AppFailure?
    operator fun invoke(request: SignRequest)
}
