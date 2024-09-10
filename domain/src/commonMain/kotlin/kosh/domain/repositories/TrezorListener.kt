package kosh.domain.repositories

import kosh.domain.entities.WalletEntity
import kosh.domain.models.trezor.Passphrase
import kosh.domain.models.trezor.PinMatrix

interface TrezorListener : Repository {

    suspend fun passphraseRequest(): Passphrase

    suspend fun pinMatrixRequest(): PinMatrix

    fun onButtonRequest(request: ButtonRequest)

    fun onConnected(id: WalletEntity.Id)

    enum class ButtonRequest {
        Other,
        FeeOverThreshold,
        ConfirmOutput,
        ResetDevice,
        ConfirmWord,
        WipeDevice,
        ProtectCall,
        SignTx,
        FirmwareCheck,
        Address,
        PublicKey,
        MnemonicWordCount,
        MnemonicInput,
        UnknownDerivationPath,
        RecoveryHomepage,
        Success,
        Warning,
        PassphraseEntry,
        PinEntry,
    }
}
