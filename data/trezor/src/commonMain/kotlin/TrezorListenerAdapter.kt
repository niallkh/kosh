package kosh.data.trezor

import com.satoshilabs.trezor.lib.protobuf.ButtonRequest
import kosh.domain.repositories.TrezorListener
import kosh.libs.trezor.TrezorManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class TrezorListenerAdapter(
    private val trezorListener: TrezorListener,
) : TrezorManager.Connection.Listener {
    override suspend fun passphraseRequest(): String? = withContext(Dispatchers.Main) {
        trezorListener.passphraseRequest().value
    }

    override suspend fun pinMatrixRequest(): String = withContext(Dispatchers.Main) {
        trezorListener.pinMatrixRequest().value
    }

    override fun onButtonRequest(type: ButtonRequest.ButtonRequestType?) {
        trezorListener.onButtonRequest(type?.toType ?: TrezorListener.ButtonRequest.Other)
    }
}

private val ButtonRequest.ButtonRequestType.toType: TrezorListener.ButtonRequest
    inline get() = when (this) {
        ButtonRequest.ButtonRequestType.ButtonRequest_Other -> TrezorListener.ButtonRequest.Other
        ButtonRequest.ButtonRequestType.ButtonRequest_FeeOverThreshold -> TrezorListener.ButtonRequest.FeeOverThreshold
        ButtonRequest.ButtonRequestType.ButtonRequest_ConfirmOutput -> TrezorListener.ButtonRequest.ConfirmOutput
        ButtonRequest.ButtonRequestType.ButtonRequest_ResetDevice -> TrezorListener.ButtonRequest.ResetDevice
        ButtonRequest.ButtonRequestType.ButtonRequest_ConfirmWord -> TrezorListener.ButtonRequest.ConfirmWord
        ButtonRequest.ButtonRequestType.ButtonRequest_WipeDevice -> TrezorListener.ButtonRequest.WipeDevice
        ButtonRequest.ButtonRequestType.ButtonRequest_ProtectCall -> TrezorListener.ButtonRequest.ProtectCall
        ButtonRequest.ButtonRequestType.ButtonRequest_SignTx -> TrezorListener.ButtonRequest.SignTx
        ButtonRequest.ButtonRequestType.ButtonRequest_FirmwareCheck -> TrezorListener.ButtonRequest.FirmwareCheck
        ButtonRequest.ButtonRequestType.ButtonRequest_Address -> TrezorListener.ButtonRequest.Address
        ButtonRequest.ButtonRequestType.ButtonRequest_PublicKey -> TrezorListener.ButtonRequest.PublicKey
        ButtonRequest.ButtonRequestType.ButtonRequest_MnemonicWordCount -> TrezorListener.ButtonRequest.MnemonicWordCount
        ButtonRequest.ButtonRequestType.ButtonRequest_MnemonicInput -> TrezorListener.ButtonRequest.MnemonicInput
        ButtonRequest.ButtonRequestType.ButtonRequest_UnknownDerivationPath -> TrezorListener.ButtonRequest.UnknownDerivationPath
        ButtonRequest.ButtonRequestType.ButtonRequest_RecoveryHomepage -> TrezorListener.ButtonRequest.RecoveryHomepage
        ButtonRequest.ButtonRequestType.ButtonRequest_Success -> TrezorListener.ButtonRequest.Success
        ButtonRequest.ButtonRequestType.ButtonRequest_Warning -> TrezorListener.ButtonRequest.Warning
        ButtonRequest.ButtonRequestType.ButtonRequest_PassphraseEntry -> TrezorListener.ButtonRequest.PassphraseEntry
        ButtonRequest.ButtonRequestType.ButtonRequest_PinEntry -> TrezorListener.ButtonRequest.PinEntry
        ButtonRequest.ButtonRequestType._Deprecated_ButtonRequest_PassphraseType -> TrezorListener.ButtonRequest.Other
    }
