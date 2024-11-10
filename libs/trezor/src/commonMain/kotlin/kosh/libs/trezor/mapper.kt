package kosh.libs.trezor

import com.satoshilabs.trezor.lib.protobuf.Address
import com.satoshilabs.trezor.lib.protobuf.ApplyFlags
import com.satoshilabs.trezor.lib.protobuf.ApplySettings
import com.satoshilabs.trezor.lib.protobuf.AuthenticateDevice
import com.satoshilabs.trezor.lib.protobuf.AuthenticityProof
import com.satoshilabs.trezor.lib.protobuf.AuthorizeCoinJoin
import com.satoshilabs.trezor.lib.protobuf.BackupDevice
import com.satoshilabs.trezor.lib.protobuf.BinanceAddress
import com.satoshilabs.trezor.lib.protobuf.BinanceCancelMsg
import com.satoshilabs.trezor.lib.protobuf.BinanceGetAddress
import com.satoshilabs.trezor.lib.protobuf.BinanceGetPublicKey
import com.satoshilabs.trezor.lib.protobuf.BinanceOrderMsg
import com.satoshilabs.trezor.lib.protobuf.BinancePublicKey
import com.satoshilabs.trezor.lib.protobuf.BinanceSignTx
import com.satoshilabs.trezor.lib.protobuf.BinanceSignedTx
import com.satoshilabs.trezor.lib.protobuf.BinanceTransferMsg
import com.satoshilabs.trezor.lib.protobuf.BinanceTxRequest
import com.satoshilabs.trezor.lib.protobuf.ButtonAck
import com.satoshilabs.trezor.lib.protobuf.ButtonRequest
import com.satoshilabs.trezor.lib.protobuf.Cancel
import com.satoshilabs.trezor.lib.protobuf.CancelAuthorization
import com.satoshilabs.trezor.lib.protobuf.CardanoAddress
import com.satoshilabs.trezor.lib.protobuf.CardanoAssetGroup
import com.satoshilabs.trezor.lib.protobuf.CardanoGetAddress
import com.satoshilabs.trezor.lib.protobuf.CardanoGetNativeScriptHash
import com.satoshilabs.trezor.lib.protobuf.CardanoGetPublicKey
import com.satoshilabs.trezor.lib.protobuf.CardanoNativeScriptHash
import com.satoshilabs.trezor.lib.protobuf.CardanoPoolOwner
import com.satoshilabs.trezor.lib.protobuf.CardanoPoolRelayParameters
import com.satoshilabs.trezor.lib.protobuf.CardanoPublicKey
import com.satoshilabs.trezor.lib.protobuf.CardanoSignTxFinished
import com.satoshilabs.trezor.lib.protobuf.CardanoSignTxInit
import com.satoshilabs.trezor.lib.protobuf.CardanoToken
import com.satoshilabs.trezor.lib.protobuf.CardanoTxAuxiliaryData
import com.satoshilabs.trezor.lib.protobuf.CardanoTxAuxiliaryDataSupplement
import com.satoshilabs.trezor.lib.protobuf.CardanoTxBodyHash
import com.satoshilabs.trezor.lib.protobuf.CardanoTxCertificate
import com.satoshilabs.trezor.lib.protobuf.CardanoTxCollateralInput
import com.satoshilabs.trezor.lib.protobuf.CardanoTxHostAck
import com.satoshilabs.trezor.lib.protobuf.CardanoTxInlineDatumChunk
import com.satoshilabs.trezor.lib.protobuf.CardanoTxInput
import com.satoshilabs.trezor.lib.protobuf.CardanoTxItemAck
import com.satoshilabs.trezor.lib.protobuf.CardanoTxMint
import com.satoshilabs.trezor.lib.protobuf.CardanoTxOutput
import com.satoshilabs.trezor.lib.protobuf.CardanoTxReferenceInput
import com.satoshilabs.trezor.lib.protobuf.CardanoTxReferenceScriptChunk
import com.satoshilabs.trezor.lib.protobuf.CardanoTxRequiredSigner
import com.satoshilabs.trezor.lib.protobuf.CardanoTxWithdrawal
import com.satoshilabs.trezor.lib.protobuf.CardanoTxWitnessRequest
import com.satoshilabs.trezor.lib.protobuf.CardanoTxWitnessResponse
import com.satoshilabs.trezor.lib.protobuf.ChangeLanguage
import com.satoshilabs.trezor.lib.protobuf.ChangePin
import com.satoshilabs.trezor.lib.protobuf.ChangeWipeCode
import com.satoshilabs.trezor.lib.protobuf.CipherKeyValue
import com.satoshilabs.trezor.lib.protobuf.CipheredKeyValue
import com.satoshilabs.trezor.lib.protobuf.CosiCommit
import com.satoshilabs.trezor.lib.protobuf.CosiCommitment
import com.satoshilabs.trezor.lib.protobuf.CosiSign
import com.satoshilabs.trezor.lib.protobuf.CosiSignature
import com.satoshilabs.trezor.lib.protobuf.DebugLinkDecision
import com.satoshilabs.trezor.lib.protobuf.DebugLinkEraseSdCard
import com.satoshilabs.trezor.lib.protobuf.DebugLinkFlashErase
import com.satoshilabs.trezor.lib.protobuf.DebugLinkGetState
import com.satoshilabs.trezor.lib.protobuf.DebugLinkLayout
import com.satoshilabs.trezor.lib.protobuf.DebugLinkLog
import com.satoshilabs.trezor.lib.protobuf.DebugLinkMemory
import com.satoshilabs.trezor.lib.protobuf.DebugLinkMemoryRead
import com.satoshilabs.trezor.lib.protobuf.DebugLinkMemoryWrite
import com.satoshilabs.trezor.lib.protobuf.DebugLinkRecordScreen
import com.satoshilabs.trezor.lib.protobuf.DebugLinkReseedRandom
import com.satoshilabs.trezor.lib.protobuf.DebugLinkResetDebugEvents
import com.satoshilabs.trezor.lib.protobuf.DebugLinkState
import com.satoshilabs.trezor.lib.protobuf.DebugLinkStop
import com.satoshilabs.trezor.lib.protobuf.DebugLinkWatchLayout
import com.satoshilabs.trezor.lib.protobuf.DebugMoneroDiagAck
import com.satoshilabs.trezor.lib.protobuf.DebugMoneroDiagRequest
import com.satoshilabs.trezor.lib.protobuf.DoPreauthorized
import com.satoshilabs.trezor.lib.protobuf.ECDHSessionKey
import com.satoshilabs.trezor.lib.protobuf.EndSession
import com.satoshilabs.trezor.lib.protobuf.Entropy
import com.satoshilabs.trezor.lib.protobuf.EntropyAck
import com.satoshilabs.trezor.lib.protobuf.EntropyRequest
import com.satoshilabs.trezor.lib.protobuf.EosGetPublicKey
import com.satoshilabs.trezor.lib.protobuf.EosPublicKey
import com.satoshilabs.trezor.lib.protobuf.EosSignTx
import com.satoshilabs.trezor.lib.protobuf.EosSignedTx
import com.satoshilabs.trezor.lib.protobuf.EosTxActionAck
import com.satoshilabs.trezor.lib.protobuf.EosTxActionRequest
import com.satoshilabs.trezor.lib.protobuf.EthereumAddress
import com.satoshilabs.trezor.lib.protobuf.EthereumGetAddress
import com.satoshilabs.trezor.lib.protobuf.EthereumGetPublicKey
import com.satoshilabs.trezor.lib.protobuf.EthereumMessageSignature
import com.satoshilabs.trezor.lib.protobuf.EthereumPublicKey
import com.satoshilabs.trezor.lib.protobuf.EthereumSignMessage
import com.satoshilabs.trezor.lib.protobuf.EthereumSignTx
import com.satoshilabs.trezor.lib.protobuf.EthereumSignTxEIP1559
import com.satoshilabs.trezor.lib.protobuf.EthereumSignTypedData
import com.satoshilabs.trezor.lib.protobuf.EthereumSignTypedHash
import com.satoshilabs.trezor.lib.protobuf.EthereumTxAck
import com.satoshilabs.trezor.lib.protobuf.EthereumTxRequest
import com.satoshilabs.trezor.lib.protobuf.EthereumTypedDataSignature
import com.satoshilabs.trezor.lib.protobuf.EthereumTypedDataStructAck
import com.satoshilabs.trezor.lib.protobuf.EthereumTypedDataStructRequest
import com.satoshilabs.trezor.lib.protobuf.EthereumTypedDataValueAck
import com.satoshilabs.trezor.lib.protobuf.EthereumTypedDataValueRequest
import com.satoshilabs.trezor.lib.protobuf.EthereumVerifyMessage
import com.satoshilabs.trezor.lib.protobuf.Failure
import com.satoshilabs.trezor.lib.protobuf.Features
import com.satoshilabs.trezor.lib.protobuf.FirmwareErase
import com.satoshilabs.trezor.lib.protobuf.FirmwareHash
import com.satoshilabs.trezor.lib.protobuf.FirmwareRequest
import com.satoshilabs.trezor.lib.protobuf.FirmwareUpload
import com.satoshilabs.trezor.lib.protobuf.GetAddress
import com.satoshilabs.trezor.lib.protobuf.GetECDHSessionKey
import com.satoshilabs.trezor.lib.protobuf.GetEntropy
import com.satoshilabs.trezor.lib.protobuf.GetFeatures
import com.satoshilabs.trezor.lib.protobuf.GetFirmwareHash
import com.satoshilabs.trezor.lib.protobuf.GetNextU2FCounter
import com.satoshilabs.trezor.lib.protobuf.GetNonce
import com.satoshilabs.trezor.lib.protobuf.GetOwnershipId
import com.satoshilabs.trezor.lib.protobuf.GetOwnershipProof
import com.satoshilabs.trezor.lib.protobuf.GetPublicKey
import com.satoshilabs.trezor.lib.protobuf.Initialize
import com.satoshilabs.trezor.lib.protobuf.LoadDevice
import com.satoshilabs.trezor.lib.protobuf.LockDevice
import com.satoshilabs.trezor.lib.protobuf.MessageSignature
import com.satoshilabs.trezor.lib.protobuf.MessageType
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_Address
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_ApplyFlags
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_ApplySettings
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_AuthenticateDevice
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_AuthenticityProof
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_AuthorizeCoinJoin
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_BackupDevice
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_BinanceAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_BinanceCancelMsg
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_BinanceGetAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_BinanceGetPublicKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_BinanceOrderMsg
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_BinancePublicKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_BinanceSignTx
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_BinanceSignedTx
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_BinanceTransferMsg
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_BinanceTxRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_ButtonAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_ButtonRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_Cancel
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CancelAuthorization
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoAssetGroup
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoGetAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoGetNativeScriptHash
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoGetPublicKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoNativeScriptHash
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoPoolOwner
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoPoolRelayParameters
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoPublicKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoSignTxFinished
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoSignTxInit
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoToken
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxAuxiliaryData
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxAuxiliaryDataSupplement
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxBodyHash
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxCertificate
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxCollateralInput
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxHostAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxInlineDatumChunk
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxInput
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxItemAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxMint
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxOutput
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxReferenceInput
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxReferenceScriptChunk
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxRequiredSigner
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxWithdrawal
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxWitnessRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CardanoTxWitnessResponse
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_ChangeLanguage
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_ChangePin
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_ChangeWipeCode
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CipherKeyValue
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CipheredKeyValue
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CosiCommit
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CosiCommitment
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CosiSign
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_CosiSignature
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugLinkDecision
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugLinkEraseSdCard
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugLinkFlashErase
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugLinkGetState
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugLinkLayout
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugLinkLog
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugLinkMemory
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugLinkMemoryRead
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugLinkMemoryWrite
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugLinkRecordScreen
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugLinkReseedRandom
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugLinkResetDebugEvents
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugLinkState
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugLinkStop
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugLinkWatchLayout
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugMoneroDiagAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DebugMoneroDiagRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_Deprecated_PassphraseStateAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_Deprecated_PassphraseStateRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_DoPreauthorized
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_ECDHSessionKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EndSession
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_Entropy
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EntropyAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EntropyRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EosGetPublicKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EosPublicKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EosSignTx
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EosSignedTx
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EosTxActionAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EosTxActionRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumGetAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumGetPublicKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumMessageSignature
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumPublicKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumSignMessage
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumSignTx
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumSignTxEIP1559
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumSignTypedData
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumSignTypedHash
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumTxAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumTxRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumTypedDataSignature
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumTypedDataStructAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumTypedDataStructRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumTypedDataValueAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumTypedDataValueRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_EthereumVerifyMessage
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_Failure
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_Features
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_FirmwareErase
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_FirmwareHash
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_FirmwareRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_FirmwareUpload
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_GetAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_GetECDHSessionKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_GetEntropy
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_GetFeatures
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_GetFirmwareHash
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_GetNextU2FCounter
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_GetNonce
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_GetOwnershipId
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_GetOwnershipProof
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_GetPublicKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_Initialize
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_LoadDevice
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_LockDevice
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MessageSignature
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroGetAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroGetTxKeyAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroGetTxKeyRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroGetWatchKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroKeyImageExportInitAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroKeyImageExportInitRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroKeyImageSyncFinalAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroKeyImageSyncFinalRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroKeyImageSyncStepAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroKeyImageSyncStepRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroLiveRefreshFinalAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroLiveRefreshFinalRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroLiveRefreshStartAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroLiveRefreshStartRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroLiveRefreshStepAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroLiveRefreshStepRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionAllInputsSetAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionAllInputsSetRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionAllOutSetAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionAllOutSetRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionFinalAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionFinalRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionInitAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionInitRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionInputViniAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionInputViniRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionSetInputAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionSetInputRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionSetOutputAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionSetOutputRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionSignInputAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroTransactionSignInputRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_MoneroWatchKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_NEMAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_NEMDecryptMessage
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_NEMDecryptedMessage
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_NEMGetAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_NEMSignTx
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_NEMSignedTx
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_NextU2FCounter
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_Nonce
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_OwnershipId
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_OwnershipProof
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_PassphraseAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_PassphraseRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_PinMatrixAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_PinMatrixRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_Ping
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_PreauthorizedRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_ProdTestT1
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_PublicKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_RebootToBootloader
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_RecoveryDevice
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_ResetDevice
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_RippleAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_RippleGetAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_RippleSignTx
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_RippleSignedTx
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_SdProtect
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_SetBusy
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_SetU2FCounter
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_ShowDeviceTutorial
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_SignIdentity
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_SignMessage
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_SignTx
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_SignedIdentity
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_SolanaAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_SolanaGetAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_SolanaGetPublicKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_SolanaPublicKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_SolanaSignTx
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_SolanaTxSignature
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarAccountMergeOp
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarAllowTrustOp
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarBumpSequenceOp
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarChangeTrustOp
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarClaimClaimableBalanceOp
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarCreateAccountOp
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarCreatePassiveSellOfferOp
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarGetAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarManageBuyOfferOp
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarManageDataOp
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarManageSellOfferOp
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarPathPaymentStrictReceiveOp
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarPathPaymentStrictSendOp
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarPaymentOp
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarSetOptionsOp
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarSignTx
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarSignedTx
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_StellarTxOpRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_Success
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_TezosAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_TezosGetAddress
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_TezosGetPublicKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_TezosPublicKey
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_TezosSignTx
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_TezosSignedTx
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_TranslationDataAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_TranslationDataRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_TxAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_TxAckPaymentRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_TxRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_UnlockBootloader
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_UnlockPath
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_UnlockedPathRequest
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_VerifyMessage
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_WebAuthnAddResidentCredential
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_WebAuthnCredentials
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_WebAuthnListResidentCredentials
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_WebAuthnRemoveResidentCredential
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_WipeDevice
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_WordAck
import com.satoshilabs.trezor.lib.protobuf.MessageType.MessageType_WordRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroAddress
import com.satoshilabs.trezor.lib.protobuf.MoneroGetAddress
import com.satoshilabs.trezor.lib.protobuf.MoneroGetTxKeyAck
import com.satoshilabs.trezor.lib.protobuf.MoneroGetTxKeyRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroGetWatchKey
import com.satoshilabs.trezor.lib.protobuf.MoneroKeyImageExportInitAck
import com.satoshilabs.trezor.lib.protobuf.MoneroKeyImageExportInitRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroKeyImageSyncFinalAck
import com.satoshilabs.trezor.lib.protobuf.MoneroKeyImageSyncFinalRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroKeyImageSyncStepAck
import com.satoshilabs.trezor.lib.protobuf.MoneroKeyImageSyncStepRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroLiveRefreshFinalAck
import com.satoshilabs.trezor.lib.protobuf.MoneroLiveRefreshFinalRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroLiveRefreshStartAck
import com.satoshilabs.trezor.lib.protobuf.MoneroLiveRefreshStartRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroLiveRefreshStepAck
import com.satoshilabs.trezor.lib.protobuf.MoneroLiveRefreshStepRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionAllInputsSetAck
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionAllInputsSetRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionAllOutSetAck
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionAllOutSetRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionFinalAck
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionFinalRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionInitAck
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionInitRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionInputViniAck
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionInputViniRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionSetInputAck
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionSetInputRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionSetOutputAck
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionSetOutputRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionSignInputAck
import com.satoshilabs.trezor.lib.protobuf.MoneroTransactionSignInputRequest
import com.satoshilabs.trezor.lib.protobuf.MoneroWatchKey
import com.satoshilabs.trezor.lib.protobuf.NEMAddress
import com.satoshilabs.trezor.lib.protobuf.NEMDecryptMessage
import com.satoshilabs.trezor.lib.protobuf.NEMDecryptedMessage
import com.satoshilabs.trezor.lib.protobuf.NEMGetAddress
import com.satoshilabs.trezor.lib.protobuf.NEMSignTx
import com.satoshilabs.trezor.lib.protobuf.NEMSignedTx
import com.satoshilabs.trezor.lib.protobuf.NextU2FCounter
import com.satoshilabs.trezor.lib.protobuf.Nonce
import com.satoshilabs.trezor.lib.protobuf.OwnershipId
import com.satoshilabs.trezor.lib.protobuf.OwnershipProof
import com.satoshilabs.trezor.lib.protobuf.PassphraseAck
import com.satoshilabs.trezor.lib.protobuf.PassphraseRequest
import com.satoshilabs.trezor.lib.protobuf.PinMatrixAck
import com.satoshilabs.trezor.lib.protobuf.PinMatrixRequest
import com.satoshilabs.trezor.lib.protobuf.Ping
import com.satoshilabs.trezor.lib.protobuf.PreauthorizedRequest
import com.satoshilabs.trezor.lib.protobuf.ProdTestT1
import com.satoshilabs.trezor.lib.protobuf.PublicKey
import com.satoshilabs.trezor.lib.protobuf.RebootToBootloader
import com.satoshilabs.trezor.lib.protobuf.RecoveryDevice
import com.satoshilabs.trezor.lib.protobuf.ResetDevice
import com.satoshilabs.trezor.lib.protobuf.RippleAddress
import com.satoshilabs.trezor.lib.protobuf.RippleGetAddress
import com.satoshilabs.trezor.lib.protobuf.RippleSignTx
import com.satoshilabs.trezor.lib.protobuf.RippleSignedTx
import com.satoshilabs.trezor.lib.protobuf.SdProtect
import com.satoshilabs.trezor.lib.protobuf.SetBusy
import com.satoshilabs.trezor.lib.protobuf.SetU2FCounter
import com.satoshilabs.trezor.lib.protobuf.ShowDeviceTutorial
import com.satoshilabs.trezor.lib.protobuf.SignIdentity
import com.satoshilabs.trezor.lib.protobuf.SignMessage
import com.satoshilabs.trezor.lib.protobuf.SignTx
import com.satoshilabs.trezor.lib.protobuf.SignedIdentity
import com.satoshilabs.trezor.lib.protobuf.StellarAccountMergeOp
import com.satoshilabs.trezor.lib.protobuf.StellarAddress
import com.satoshilabs.trezor.lib.protobuf.StellarAllowTrustOp
import com.satoshilabs.trezor.lib.protobuf.StellarBumpSequenceOp
import com.satoshilabs.trezor.lib.protobuf.StellarChangeTrustOp
import com.satoshilabs.trezor.lib.protobuf.StellarClaimClaimableBalanceOp
import com.satoshilabs.trezor.lib.protobuf.StellarCreateAccountOp
import com.satoshilabs.trezor.lib.protobuf.StellarCreatePassiveSellOfferOp
import com.satoshilabs.trezor.lib.protobuf.StellarGetAddress
import com.satoshilabs.trezor.lib.protobuf.StellarManageBuyOfferOp
import com.satoshilabs.trezor.lib.protobuf.StellarManageDataOp
import com.satoshilabs.trezor.lib.protobuf.StellarManageSellOfferOp
import com.satoshilabs.trezor.lib.protobuf.StellarPathPaymentStrictReceiveOp
import com.satoshilabs.trezor.lib.protobuf.StellarPathPaymentStrictSendOp
import com.satoshilabs.trezor.lib.protobuf.StellarPaymentOp
import com.satoshilabs.trezor.lib.protobuf.StellarSetOptionsOp
import com.satoshilabs.trezor.lib.protobuf.StellarSignTx
import com.satoshilabs.trezor.lib.protobuf.StellarSignedTx
import com.satoshilabs.trezor.lib.protobuf.StellarTxOpRequest
import com.satoshilabs.trezor.lib.protobuf.Success
import com.satoshilabs.trezor.lib.protobuf.TezosAddress
import com.satoshilabs.trezor.lib.protobuf.TezosGetAddress
import com.satoshilabs.trezor.lib.protobuf.TezosGetPublicKey
import com.satoshilabs.trezor.lib.protobuf.TezosPublicKey
import com.satoshilabs.trezor.lib.protobuf.TezosSignTx
import com.satoshilabs.trezor.lib.protobuf.TezosSignedTx
import com.satoshilabs.trezor.lib.protobuf.TranslationDataAck
import com.satoshilabs.trezor.lib.protobuf.TranslationDataRequest
import com.satoshilabs.trezor.lib.protobuf.TxAck
import com.satoshilabs.trezor.lib.protobuf.TxAckPaymentRequest
import com.satoshilabs.trezor.lib.protobuf.TxRequest
import com.satoshilabs.trezor.lib.protobuf.UnlockBootloader
import com.satoshilabs.trezor.lib.protobuf.UnlockPath
import com.satoshilabs.trezor.lib.protobuf.UnlockedPathRequest
import com.satoshilabs.trezor.lib.protobuf.VerifyMessage
import com.satoshilabs.trezor.lib.protobuf.WebAuthnAddResidentCredential
import com.satoshilabs.trezor.lib.protobuf.WebAuthnCredentials
import com.satoshilabs.trezor.lib.protobuf.WebAuthnListResidentCredentials
import com.satoshilabs.trezor.lib.protobuf.WebAuthnRemoveResidentCredential
import com.satoshilabs.trezor.lib.protobuf.WipeDevice
import com.satoshilabs.trezor.lib.protobuf.WordAck
import com.satoshilabs.trezor.lib.protobuf.WordRequest
import com.squareup.wire.Message
import hw.trezor.messages.solana.SolanaAddress
import hw.trezor.messages.solana.SolanaGetAddress
import hw.trezor.messages.solana.SolanaGetPublicKey
import hw.trezor.messages.solana.SolanaPublicKey
import hw.trezor.messages.solana.SolanaSignTx
import hw.trezor.messages.solana.SolanaTxSignature

internal fun MessageType.toAdapter() = when (this) {
    MessageType_Initialize -> Initialize.ADAPTER
    MessageType_Ping -> Ping.ADAPTER
    MessageType_Success -> Success.ADAPTER
    MessageType_Failure -> Failure.ADAPTER
    MessageType_ChangePin -> ChangePin.ADAPTER
    MessageType_WipeDevice -> WipeDevice.ADAPTER
    MessageType_GetEntropy -> GetEntropy.ADAPTER
    MessageType_Entropy -> Entropy.ADAPTER
    MessageType_LoadDevice -> LoadDevice.ADAPTER
    MessageType_ResetDevice -> ResetDevice.ADAPTER
    MessageType_SetBusy -> SetBusy.ADAPTER
    MessageType_Features -> Features.ADAPTER
    MessageType_PinMatrixRequest -> PinMatrixRequest.ADAPTER
    MessageType_PinMatrixAck -> PinMatrixAck.ADAPTER
    MessageType_Cancel -> Cancel.ADAPTER
    MessageType_LockDevice -> LockDevice.ADAPTER
    MessageType_ApplySettings -> ApplySettings.ADAPTER
    MessageType_ButtonRequest -> ButtonRequest.ADAPTER
    MessageType_ButtonAck -> ButtonAck.ADAPTER
    MessageType_ApplyFlags -> ApplyFlags.ADAPTER
    MessageType_GetNonce -> GetNonce.ADAPTER
    MessageType_Nonce -> Nonce.ADAPTER
    MessageType_BackupDevice -> BackupDevice.ADAPTER
    MessageType_EntropyRequest -> EntropyRequest.ADAPTER
    MessageType_EntropyAck -> EntropyAck.ADAPTER
    MessageType_PassphraseRequest -> PassphraseRequest.ADAPTER
    MessageType_PassphraseAck -> PassphraseAck.ADAPTER
    MessageType_RecoveryDevice -> RecoveryDevice.ADAPTER
    MessageType_WordRequest -> WordRequest.ADAPTER
    MessageType_WordAck -> WordAck.ADAPTER
    MessageType_GetFeatures -> GetFeatures.ADAPTER
    MessageType_SdProtect -> SdProtect.ADAPTER
    MessageType_ChangeWipeCode -> ChangeWipeCode.ADAPTER
    MessageType_EndSession -> EndSession.ADAPTER
    MessageType_DoPreauthorized -> DoPreauthorized.ADAPTER
    MessageType_PreauthorizedRequest -> PreauthorizedRequest.ADAPTER
    MessageType_CancelAuthorization -> CancelAuthorization.ADAPTER
    MessageType_RebootToBootloader -> RebootToBootloader.ADAPTER
    MessageType_GetFirmwareHash -> GetFirmwareHash.ADAPTER
    MessageType_FirmwareHash -> FirmwareHash.ADAPTER
    MessageType_UnlockPath -> UnlockPath.ADAPTER
    MessageType_UnlockedPathRequest -> UnlockedPathRequest.ADAPTER
    MessageType_ShowDeviceTutorial -> ShowDeviceTutorial.ADAPTER
    MessageType_UnlockBootloader -> UnlockBootloader.ADAPTER
    MessageType_AuthenticateDevice -> AuthenticateDevice.ADAPTER
    MessageType_AuthenticityProof -> AuthenticityProof.ADAPTER
    MessageType_ChangeLanguage -> ChangeLanguage.ADAPTER
    MessageType_TranslationDataRequest -> TranslationDataRequest.ADAPTER
    MessageType_TranslationDataAck -> TranslationDataAck.ADAPTER
    MessageType_SetU2FCounter -> SetU2FCounter.ADAPTER
    MessageType_GetNextU2FCounter -> GetNextU2FCounter.ADAPTER
    MessageType_NextU2FCounter -> NextU2FCounter.ADAPTER
    MessageType_FirmwareErase -> FirmwareErase.ADAPTER
    MessageType_FirmwareUpload -> FirmwareUpload.ADAPTER
    MessageType_FirmwareRequest -> FirmwareRequest.ADAPTER
    MessageType_ProdTestT1 -> ProdTestT1.ADAPTER
    MessageType_GetPublicKey -> GetPublicKey.ADAPTER
    MessageType_PublicKey -> PublicKey.ADAPTER
    MessageType_SignTx -> SignTx.ADAPTER
    MessageType_TxRequest -> TxRequest.ADAPTER
    MessageType_TxAck -> TxAck.ADAPTER
    MessageType_GetAddress -> GetAddress.ADAPTER
    MessageType_Address -> Address.ADAPTER
    MessageType_TxAckPaymentRequest -> TxAckPaymentRequest.ADAPTER
    MessageType_SignMessage -> SignMessage.ADAPTER
    MessageType_VerifyMessage -> VerifyMessage.ADAPTER
    MessageType_MessageSignature -> MessageSignature.ADAPTER
    MessageType_GetOwnershipId -> GetOwnershipId.ADAPTER
    MessageType_OwnershipId -> OwnershipId.ADAPTER
    MessageType_GetOwnershipProof -> GetOwnershipProof.ADAPTER
    MessageType_OwnershipProof -> OwnershipProof.ADAPTER
    MessageType_AuthorizeCoinJoin -> AuthorizeCoinJoin.ADAPTER
    MessageType_CipherKeyValue -> CipherKeyValue.ADAPTER
    MessageType_CipheredKeyValue -> CipheredKeyValue.ADAPTER
    MessageType_SignIdentity -> SignIdentity.ADAPTER
    MessageType_SignedIdentity -> SignedIdentity.ADAPTER
    MessageType_GetECDHSessionKey -> GetECDHSessionKey.ADAPTER
    MessageType_ECDHSessionKey -> ECDHSessionKey.ADAPTER
    MessageType_CosiCommit -> CosiCommit.ADAPTER
    MessageType_CosiCommitment -> CosiCommitment.ADAPTER
    MessageType_CosiSign -> CosiSign.ADAPTER
    MessageType_CosiSignature -> CosiSignature.ADAPTER
    MessageType_DebugLinkDecision -> DebugLinkDecision.ADAPTER
    MessageType_DebugLinkGetState -> DebugLinkGetState.ADAPTER
    MessageType_DebugLinkState -> DebugLinkState.ADAPTER
    MessageType_DebugLinkStop -> DebugLinkStop.ADAPTER
    MessageType_DebugLinkLog -> DebugLinkLog.ADAPTER
    MessageType_DebugLinkMemoryRead -> DebugLinkMemoryRead.ADAPTER
    MessageType_DebugLinkMemory -> DebugLinkMemory.ADAPTER
    MessageType_DebugLinkMemoryWrite -> DebugLinkMemoryWrite.ADAPTER
    MessageType_DebugLinkFlashErase -> DebugLinkFlashErase.ADAPTER
    MessageType_DebugLinkLayout -> DebugLinkLayout.ADAPTER
    MessageType_DebugLinkReseedRandom -> DebugLinkReseedRandom.ADAPTER
    MessageType_DebugLinkRecordScreen -> DebugLinkRecordScreen.ADAPTER
    MessageType_DebugLinkEraseSdCard -> DebugLinkEraseSdCard.ADAPTER
    MessageType_DebugLinkWatchLayout -> DebugLinkWatchLayout.ADAPTER
    MessageType_DebugLinkResetDebugEvents -> DebugLinkResetDebugEvents.ADAPTER
    MessageType_EthereumGetPublicKey -> EthereumGetPublicKey.ADAPTER
    MessageType_EthereumPublicKey -> EthereumPublicKey.ADAPTER
    MessageType_EthereumGetAddress -> EthereumGetAddress.ADAPTER
    MessageType_EthereumAddress -> EthereumAddress.ADAPTER
    MessageType_EthereumSignTx -> EthereumSignTx.ADAPTER
    MessageType_EthereumSignTxEIP1559 -> EthereumSignTxEIP1559.ADAPTER
    MessageType_EthereumTxRequest -> EthereumTxRequest.ADAPTER
    MessageType_EthereumTxAck -> EthereumTxAck.ADAPTER
    MessageType_EthereumSignMessage -> EthereumSignMessage.ADAPTER
    MessageType_EthereumVerifyMessage -> EthereumVerifyMessage.ADAPTER
    MessageType_EthereumMessageSignature -> EthereumMessageSignature.ADAPTER
    MessageType_EthereumSignTypedData -> EthereumSignTypedData.ADAPTER
    MessageType_EthereumTypedDataStructRequest -> EthereumTypedDataStructRequest.ADAPTER
    MessageType_EthereumTypedDataStructAck -> EthereumTypedDataStructAck.ADAPTER
    MessageType_EthereumTypedDataValueRequest -> EthereumTypedDataValueRequest.ADAPTER
    MessageType_EthereumTypedDataValueAck -> EthereumTypedDataValueAck.ADAPTER
    MessageType_EthereumTypedDataSignature -> EthereumTypedDataSignature.ADAPTER
    MessageType_EthereumSignTypedHash -> EthereumSignTypedHash.ADAPTER
    MessageType_NEMGetAddress -> NEMGetAddress.ADAPTER
    MessageType_NEMAddress -> NEMAddress.ADAPTER
    MessageType_NEMSignTx -> NEMSignTx.ADAPTER
    MessageType_NEMSignedTx -> NEMSignedTx.ADAPTER
    MessageType_NEMDecryptMessage -> NEMDecryptMessage.ADAPTER
    MessageType_NEMDecryptedMessage -> NEMDecryptedMessage.ADAPTER
    MessageType_TezosGetAddress -> TezosGetAddress.ADAPTER
    MessageType_TezosAddress -> TezosAddress.ADAPTER
    MessageType_TezosSignTx -> TezosSignTx.ADAPTER
    MessageType_TezosSignedTx -> TezosSignedTx.ADAPTER
    MessageType_TezosGetPublicKey -> TezosGetPublicKey.ADAPTER
    MessageType_TezosPublicKey -> TezosPublicKey.ADAPTER
    MessageType_StellarSignTx -> StellarSignTx.ADAPTER
    MessageType_StellarTxOpRequest -> StellarTxOpRequest.ADAPTER
    MessageType_StellarGetAddress -> StellarGetAddress.ADAPTER
    MessageType_StellarAddress -> StellarAddress.ADAPTER
    MessageType_StellarCreateAccountOp -> StellarCreateAccountOp.ADAPTER
    MessageType_StellarPaymentOp -> StellarPaymentOp.ADAPTER
    MessageType_StellarPathPaymentStrictReceiveOp -> StellarPathPaymentStrictReceiveOp.ADAPTER
    MessageType_StellarManageSellOfferOp -> StellarManageSellOfferOp.ADAPTER
    MessageType_StellarCreatePassiveSellOfferOp -> StellarCreatePassiveSellOfferOp.ADAPTER
    MessageType_StellarSetOptionsOp -> StellarSetOptionsOp.ADAPTER
    MessageType_StellarChangeTrustOp -> StellarChangeTrustOp.ADAPTER
    MessageType_StellarAllowTrustOp -> StellarAllowTrustOp.ADAPTER
    MessageType_StellarAccountMergeOp -> StellarAccountMergeOp.ADAPTER
    MessageType_StellarManageDataOp -> StellarManageDataOp.ADAPTER
    MessageType_StellarBumpSequenceOp -> StellarBumpSequenceOp.ADAPTER
    MessageType_StellarManageBuyOfferOp -> StellarManageBuyOfferOp.ADAPTER
    MessageType_StellarPathPaymentStrictSendOp -> StellarPathPaymentStrictSendOp.ADAPTER
    MessageType_StellarClaimClaimableBalanceOp -> StellarClaimClaimableBalanceOp.ADAPTER
    MessageType_StellarSignedTx -> StellarSignedTx.ADAPTER
    MessageType_CardanoGetPublicKey -> CardanoGetPublicKey.ADAPTER
    MessageType_CardanoPublicKey -> CardanoPublicKey.ADAPTER
    MessageType_CardanoGetAddress -> CardanoGetAddress.ADAPTER
    MessageType_CardanoAddress -> CardanoAddress.ADAPTER
    MessageType_CardanoTxItemAck -> CardanoTxItemAck.ADAPTER
    MessageType_CardanoTxAuxiliaryDataSupplement -> CardanoTxAuxiliaryDataSupplement.ADAPTER
    MessageType_CardanoTxWitnessRequest -> CardanoTxWitnessRequest.ADAPTER
    MessageType_CardanoTxWitnessResponse -> CardanoTxWitnessResponse.ADAPTER
    MessageType_CardanoTxHostAck -> CardanoTxHostAck.ADAPTER
    MessageType_CardanoTxBodyHash -> CardanoTxBodyHash.ADAPTER
    MessageType_CardanoSignTxFinished -> CardanoSignTxFinished.ADAPTER
    MessageType_CardanoSignTxInit -> CardanoSignTxInit.ADAPTER
    MessageType_CardanoTxInput -> CardanoTxInput.ADAPTER
    MessageType_CardanoTxOutput -> CardanoTxOutput.ADAPTER
    MessageType_CardanoAssetGroup -> CardanoAssetGroup.ADAPTER
    MessageType_CardanoToken -> CardanoToken.ADAPTER
    MessageType_CardanoTxCertificate -> CardanoTxCertificate.ADAPTER
    MessageType_CardanoTxWithdrawal -> CardanoTxWithdrawal.ADAPTER
    MessageType_CardanoTxAuxiliaryData -> CardanoTxAuxiliaryData.ADAPTER
    MessageType_CardanoPoolOwner -> CardanoPoolOwner.ADAPTER
    MessageType_CardanoPoolRelayParameters -> CardanoPoolRelayParameters.ADAPTER
    MessageType_CardanoGetNativeScriptHash -> CardanoGetNativeScriptHash.ADAPTER
    MessageType_CardanoNativeScriptHash -> CardanoNativeScriptHash.ADAPTER
    MessageType_CardanoTxMint -> CardanoTxMint.ADAPTER
    MessageType_CardanoTxCollateralInput -> CardanoTxCollateralInput.ADAPTER
    MessageType_CardanoTxRequiredSigner -> CardanoTxRequiredSigner.ADAPTER
    MessageType_CardanoTxInlineDatumChunk -> CardanoTxInlineDatumChunk.ADAPTER
    MessageType_CardanoTxReferenceScriptChunk -> CardanoTxReferenceScriptChunk.ADAPTER
    MessageType_CardanoTxReferenceInput -> CardanoTxReferenceInput.ADAPTER
    MessageType_RippleGetAddress -> RippleGetAddress.ADAPTER
    MessageType_RippleAddress -> RippleAddress.ADAPTER
    MessageType_RippleSignTx -> RippleSignTx.ADAPTER
    MessageType_RippleSignedTx -> RippleSignedTx.ADAPTER
    MessageType_MoneroTransactionInitRequest -> MoneroTransactionInitRequest.ADAPTER
    MessageType_MoneroTransactionInitAck -> MoneroTransactionInitAck.ADAPTER
    MessageType_MoneroTransactionSetInputRequest -> MoneroTransactionSetInputRequest.ADAPTER
    MessageType_MoneroTransactionSetInputAck -> MoneroTransactionSetInputAck.ADAPTER
    MessageType_MoneroTransactionInputViniRequest -> MoneroTransactionInputViniRequest.ADAPTER
    MessageType_MoneroTransactionInputViniAck -> MoneroTransactionInputViniAck.ADAPTER
    MessageType_MoneroTransactionAllInputsSetRequest -> MoneroTransactionAllInputsSetRequest.ADAPTER
    MessageType_MoneroTransactionAllInputsSetAck -> MoneroTransactionAllInputsSetAck.ADAPTER
    MessageType_MoneroTransactionSetOutputRequest -> MoneroTransactionSetOutputRequest.ADAPTER
    MessageType_MoneroTransactionSetOutputAck -> MoneroTransactionSetOutputAck.ADAPTER
    MessageType_MoneroTransactionAllOutSetRequest -> MoneroTransactionAllOutSetRequest.ADAPTER
    MessageType_MoneroTransactionAllOutSetAck -> MoneroTransactionAllOutSetAck.ADAPTER
    MessageType_MoneroTransactionSignInputRequest -> MoneroTransactionSignInputRequest.ADAPTER
    MessageType_MoneroTransactionSignInputAck -> MoneroTransactionSignInputAck.ADAPTER
    MessageType_MoneroTransactionFinalRequest -> MoneroTransactionFinalRequest.ADAPTER
    MessageType_MoneroTransactionFinalAck -> MoneroTransactionFinalAck.ADAPTER
    MessageType_MoneroKeyImageExportInitRequest -> MoneroKeyImageExportInitRequest.ADAPTER
    MessageType_MoneroKeyImageExportInitAck -> MoneroKeyImageExportInitAck.ADAPTER
    MessageType_MoneroKeyImageSyncStepRequest -> MoneroKeyImageSyncStepRequest.ADAPTER
    MessageType_MoneroKeyImageSyncStepAck -> MoneroKeyImageSyncStepAck.ADAPTER
    MessageType_MoneroKeyImageSyncFinalRequest -> MoneroKeyImageSyncFinalRequest.ADAPTER
    MessageType_MoneroKeyImageSyncFinalAck -> MoneroKeyImageSyncFinalAck.ADAPTER
    MessageType_MoneroGetAddress -> MoneroGetAddress.ADAPTER
    MessageType_MoneroAddress -> MoneroAddress.ADAPTER
    MessageType_MoneroGetWatchKey -> MoneroGetWatchKey.ADAPTER
    MessageType_MoneroWatchKey -> MoneroWatchKey.ADAPTER
    MessageType_DebugMoneroDiagRequest -> DebugMoneroDiagRequest.ADAPTER
    MessageType_DebugMoneroDiagAck -> DebugMoneroDiagAck.ADAPTER
    MessageType_MoneroGetTxKeyRequest -> MoneroGetTxKeyRequest.ADAPTER
    MessageType_MoneroGetTxKeyAck -> MoneroGetTxKeyAck.ADAPTER
    MessageType_MoneroLiveRefreshStartRequest -> MoneroLiveRefreshStartRequest.ADAPTER
    MessageType_MoneroLiveRefreshStartAck -> MoneroLiveRefreshStartAck.ADAPTER
    MessageType_MoneroLiveRefreshStepRequest -> MoneroLiveRefreshStepRequest.ADAPTER
    MessageType_MoneroLiveRefreshStepAck -> MoneroLiveRefreshStepAck.ADAPTER
    MessageType_MoneroLiveRefreshFinalRequest -> MoneroLiveRefreshFinalRequest.ADAPTER
    MessageType_MoneroLiveRefreshFinalAck -> MoneroLiveRefreshFinalAck.ADAPTER
    MessageType_EosGetPublicKey -> EosGetPublicKey.ADAPTER
    MessageType_EosPublicKey -> EosPublicKey.ADAPTER
    MessageType_EosSignTx -> EosSignTx.ADAPTER
    MessageType_EosTxActionRequest -> EosTxActionRequest.ADAPTER
    MessageType_EosTxActionAck -> EosTxActionAck.ADAPTER
    MessageType_EosSignedTx -> EosSignedTx.ADAPTER
    MessageType_BinanceGetAddress -> BinanceGetAddress.ADAPTER
    MessageType_BinanceAddress -> BinanceAddress.ADAPTER
    MessageType_BinanceGetPublicKey -> BinanceGetPublicKey.ADAPTER
    MessageType_BinancePublicKey -> BinancePublicKey.ADAPTER
    MessageType_BinanceSignTx -> BinanceSignTx.ADAPTER
    MessageType_BinanceTxRequest -> BinanceTxRequest.ADAPTER
    MessageType_BinanceTransferMsg -> BinanceTransferMsg.ADAPTER
    MessageType_BinanceOrderMsg -> BinanceOrderMsg.ADAPTER
    MessageType_BinanceCancelMsg -> BinanceCancelMsg.ADAPTER
    MessageType_BinanceSignedTx -> BinanceSignedTx.ADAPTER
    MessageType_WebAuthnListResidentCredentials -> WebAuthnListResidentCredentials.ADAPTER
    MessageType_WebAuthnCredentials -> WebAuthnCredentials.ADAPTER
    MessageType_WebAuthnAddResidentCredential -> WebAuthnAddResidentCredential.ADAPTER
    MessageType_WebAuthnRemoveResidentCredential -> WebAuthnRemoveResidentCredential.ADAPTER
    MessageType_SolanaGetPublicKey -> SolanaGetPublicKey.ADAPTER
    MessageType_SolanaPublicKey -> SolanaPublicKey.ADAPTER
    MessageType_SolanaGetAddress -> SolanaGetAddress.ADAPTER
    MessageType_SolanaAddress -> SolanaAddress.ADAPTER
    MessageType_SolanaSignTx -> SolanaSignTx.ADAPTER
    MessageType_SolanaTxSignature -> SolanaTxSignature.ADAPTER
    MessageType_Deprecated_PassphraseStateRequest,
    MessageType_Deprecated_PassphraseStateAck,
        -> error("Deprecated message type")
}

internal fun Message<*, *>.toMessageType() = when (this) {
    is Initialize -> MessageType_Initialize
    is Ping -> MessageType_Ping
    is Success -> MessageType_Success
    is Failure -> MessageType_Failure
    is ChangePin -> MessageType_ChangePin
    is WipeDevice -> MessageType_WipeDevice
    is GetEntropy -> MessageType_GetEntropy
    is Entropy -> MessageType_Entropy
    is LoadDevice -> MessageType_LoadDevice
    is ResetDevice -> MessageType_ResetDevice
    is SetBusy -> MessageType_SetBusy
    is Features -> MessageType_Features
    is PinMatrixRequest -> MessageType_PinMatrixRequest
    is PinMatrixAck -> MessageType_PinMatrixAck
    is Cancel -> MessageType_Cancel
    is LockDevice -> MessageType_LockDevice
    is ApplySettings -> MessageType_ApplySettings
    is ButtonRequest -> MessageType_ButtonRequest
    is ButtonAck -> MessageType_ButtonAck
    is ApplyFlags -> MessageType_ApplyFlags
    is GetNonce -> MessageType_GetNonce
    is Nonce -> MessageType_Nonce
    is BackupDevice -> MessageType_BackupDevice
    is EntropyRequest -> MessageType_EntropyRequest
    is EntropyAck -> MessageType_EntropyAck
    is PassphraseRequest -> MessageType_PassphraseRequest
    is PassphraseAck -> MessageType_PassphraseAck
    is RecoveryDevice -> MessageType_RecoveryDevice
    is WordRequest -> MessageType_WordRequest
    is WordAck -> MessageType_WordAck
    is GetFeatures -> MessageType_GetFeatures
    is SdProtect -> MessageType_SdProtect
    is ChangeWipeCode -> MessageType_ChangeWipeCode
    is EndSession -> MessageType_EndSession
    is DoPreauthorized -> MessageType_DoPreauthorized
    is PreauthorizedRequest -> MessageType_PreauthorizedRequest
    is CancelAuthorization -> MessageType_CancelAuthorization
    is RebootToBootloader -> MessageType_RebootToBootloader
    is GetFirmwareHash -> MessageType_GetFirmwareHash
    is FirmwareHash -> MessageType_FirmwareHash
    is UnlockPath -> MessageType_UnlockPath
    is UnlockedPathRequest -> MessageType_UnlockedPathRequest
    is ShowDeviceTutorial -> MessageType_ShowDeviceTutorial
    is UnlockBootloader -> MessageType_UnlockBootloader
    is AuthenticateDevice -> MessageType_AuthenticateDevice
    is AuthenticityProof -> MessageType_AuthenticityProof
    is ChangeLanguage -> MessageType_ChangeLanguage
    is TranslationDataRequest -> MessageType_TranslationDataRequest
    is TranslationDataAck -> MessageType_TranslationDataAck
    is SetU2FCounter -> MessageType_SetU2FCounter
    is GetNextU2FCounter -> MessageType_GetNextU2FCounter
    is NextU2FCounter -> MessageType_NextU2FCounter
    is FirmwareErase -> MessageType_FirmwareErase
    is FirmwareUpload -> MessageType_FirmwareUpload
    is FirmwareRequest -> MessageType_FirmwareRequest
    is ProdTestT1 -> MessageType_ProdTestT1
    is GetPublicKey -> MessageType_GetPublicKey
    is PublicKey -> MessageType_PublicKey
    is SignTx -> MessageType_SignTx
    is TxRequest -> MessageType_TxRequest
    is TxAck -> MessageType_TxAck
    is GetAddress -> MessageType_GetAddress
    is Address -> MessageType_Address
    is TxAckPaymentRequest -> MessageType_TxAckPaymentRequest
    is SignMessage -> MessageType_SignMessage
    is VerifyMessage -> MessageType_VerifyMessage
    is MessageSignature -> MessageType_MessageSignature
    is GetOwnershipId -> MessageType_GetOwnershipId
    is OwnershipId -> MessageType_OwnershipId
    is GetOwnershipProof -> MessageType_GetOwnershipProof
    is OwnershipProof -> MessageType_OwnershipProof
    is AuthorizeCoinJoin -> MessageType_AuthorizeCoinJoin
    is CipherKeyValue -> MessageType_CipherKeyValue
    is CipheredKeyValue -> MessageType_CipheredKeyValue
    is SignIdentity -> MessageType_SignIdentity
    is SignedIdentity -> MessageType_SignedIdentity
    is GetECDHSessionKey -> MessageType_GetECDHSessionKey
    is ECDHSessionKey -> MessageType_ECDHSessionKey
    is CosiCommit -> MessageType_CosiCommit
    is CosiCommitment -> MessageType_CosiCommitment
    is CosiSign -> MessageType_CosiSign
    is CosiSignature -> MessageType_CosiSignature
    is DebugLinkDecision -> MessageType_DebugLinkDecision
    is DebugLinkGetState -> MessageType_DebugLinkGetState
    is DebugLinkState -> MessageType_DebugLinkState
    is DebugLinkStop -> MessageType_DebugLinkStop
    is DebugLinkLog -> MessageType_DebugLinkLog
    is DebugLinkMemoryRead -> MessageType_DebugLinkMemoryRead
    is DebugLinkMemory -> MessageType_DebugLinkMemory
    is DebugLinkMemoryWrite -> MessageType_DebugLinkMemoryWrite
    is DebugLinkFlashErase -> MessageType_DebugLinkFlashErase
    is DebugLinkLayout -> MessageType_DebugLinkLayout
    is DebugLinkReseedRandom -> MessageType_DebugLinkReseedRandom
    is DebugLinkRecordScreen -> MessageType_DebugLinkRecordScreen
    is DebugLinkEraseSdCard -> MessageType_DebugLinkEraseSdCard
    is DebugLinkWatchLayout -> MessageType_DebugLinkWatchLayout
    is DebugLinkResetDebugEvents -> MessageType_DebugLinkResetDebugEvents
    is EthereumGetPublicKey -> MessageType_EthereumGetPublicKey
    is EthereumPublicKey -> MessageType_EthereumPublicKey
    is EthereumGetAddress -> MessageType_EthereumGetAddress
    is EthereumAddress -> MessageType_EthereumAddress
    is EthereumSignTx -> MessageType_EthereumSignTx
    is EthereumSignTxEIP1559 -> MessageType_EthereumSignTxEIP1559
    is EthereumTxRequest -> MessageType_EthereumTxRequest
    is EthereumTxAck -> MessageType_EthereumTxAck
    is EthereumSignMessage -> MessageType_EthereumSignMessage
    is EthereumVerifyMessage -> MessageType_EthereumVerifyMessage
    is EthereumMessageSignature -> MessageType_EthereumMessageSignature
    is EthereumSignTypedData -> MessageType_EthereumSignTypedData
    is EthereumTypedDataStructRequest -> MessageType_EthereumTypedDataStructRequest
    is EthereumTypedDataStructAck -> MessageType_EthereumTypedDataStructAck
    is EthereumTypedDataValueRequest -> MessageType_EthereumTypedDataValueRequest
    is EthereumTypedDataValueAck -> MessageType_EthereumTypedDataValueAck
    is EthereumTypedDataSignature -> MessageType_EthereumTypedDataSignature
    is EthereumSignTypedHash -> MessageType_EthereumSignTypedHash
    is NEMGetAddress -> MessageType_NEMGetAddress
    is NEMAddress -> MessageType_NEMAddress
    is NEMSignTx -> MessageType_NEMSignTx
    is NEMSignedTx -> MessageType_NEMSignedTx
    is NEMDecryptMessage -> MessageType_NEMDecryptMessage
    is NEMDecryptedMessage -> MessageType_NEMDecryptedMessage
    is TezosGetAddress -> MessageType_TezosGetAddress
    is TezosAddress -> MessageType_TezosAddress
    is TezosSignTx -> MessageType_TezosSignTx
    is TezosSignedTx -> MessageType_TezosSignedTx
    is TezosGetPublicKey -> MessageType_TezosGetPublicKey
    is TezosPublicKey -> MessageType_TezosPublicKey
    is StellarSignTx -> MessageType_StellarSignTx
    is StellarTxOpRequest -> MessageType_StellarTxOpRequest
    is StellarGetAddress -> MessageType_StellarGetAddress
    is StellarAddress -> MessageType_StellarAddress
    is StellarCreateAccountOp -> MessageType_StellarCreateAccountOp
    is StellarPaymentOp -> MessageType_StellarPaymentOp
    is StellarPathPaymentStrictReceiveOp -> MessageType_StellarPathPaymentStrictReceiveOp
    is StellarManageSellOfferOp -> MessageType_StellarManageSellOfferOp
    is StellarCreatePassiveSellOfferOp -> MessageType_StellarCreatePassiveSellOfferOp
    is StellarSetOptionsOp -> MessageType_StellarSetOptionsOp
    is StellarChangeTrustOp -> MessageType_StellarChangeTrustOp
    is StellarAllowTrustOp -> MessageType_StellarAllowTrustOp
    is StellarAccountMergeOp -> MessageType_StellarAccountMergeOp
    is StellarManageDataOp -> MessageType_StellarManageDataOp
    is StellarBumpSequenceOp -> MessageType_StellarBumpSequenceOp
    is StellarManageBuyOfferOp -> MessageType_StellarManageBuyOfferOp
    is StellarPathPaymentStrictSendOp -> MessageType_StellarPathPaymentStrictSendOp
    is StellarClaimClaimableBalanceOp -> MessageType_StellarClaimClaimableBalanceOp
    is StellarSignedTx -> MessageType_StellarSignedTx
    is CardanoGetPublicKey -> MessageType_CardanoGetPublicKey
    is CardanoPublicKey -> MessageType_CardanoPublicKey
    is CardanoGetAddress -> MessageType_CardanoGetAddress
    is CardanoAddress -> MessageType_CardanoAddress
    is CardanoTxItemAck -> MessageType_CardanoTxItemAck
    is CardanoTxAuxiliaryDataSupplement -> MessageType_CardanoTxAuxiliaryDataSupplement
    is CardanoTxWitnessRequest -> MessageType_CardanoTxWitnessRequest
    is CardanoTxWitnessResponse -> MessageType_CardanoTxWitnessResponse
    is CardanoTxHostAck -> MessageType_CardanoTxHostAck
    is CardanoTxBodyHash -> MessageType_CardanoTxBodyHash
    is CardanoSignTxFinished -> MessageType_CardanoSignTxFinished
    is CardanoSignTxInit -> MessageType_CardanoSignTxInit
    is CardanoTxInput -> MessageType_CardanoTxInput
    is CardanoTxOutput -> MessageType_CardanoTxOutput
    is CardanoAssetGroup -> MessageType_CardanoAssetGroup
    is CardanoToken -> MessageType_CardanoToken
    is CardanoTxCertificate -> MessageType_CardanoTxCertificate
    is CardanoTxWithdrawal -> MessageType_CardanoTxWithdrawal
    is CardanoTxAuxiliaryData -> MessageType_CardanoTxAuxiliaryData
    is CardanoPoolOwner -> MessageType_CardanoPoolOwner
    is CardanoPoolRelayParameters -> MessageType_CardanoPoolRelayParameters
    is CardanoGetNativeScriptHash -> MessageType_CardanoGetNativeScriptHash
    is CardanoNativeScriptHash -> MessageType_CardanoNativeScriptHash
    is CardanoTxMint -> MessageType_CardanoTxMint
    is CardanoTxCollateralInput -> MessageType_CardanoTxCollateralInput
    is CardanoTxRequiredSigner -> MessageType_CardanoTxRequiredSigner
    is CardanoTxInlineDatumChunk -> MessageType_CardanoTxInlineDatumChunk
    is CardanoTxReferenceScriptChunk -> MessageType_CardanoTxReferenceScriptChunk
    is CardanoTxReferenceInput -> MessageType_CardanoTxReferenceInput
    is RippleGetAddress -> MessageType_RippleGetAddress
    is RippleAddress -> MessageType_RippleAddress
    is RippleSignTx -> MessageType_RippleSignTx
    is RippleSignedTx -> MessageType_RippleSignedTx
    is MoneroTransactionInitRequest -> MessageType_MoneroTransactionInitRequest
    is MoneroTransactionInitAck -> MessageType_MoneroTransactionInitAck
    is MoneroTransactionSetInputRequest -> MessageType_MoneroTransactionSetInputRequest
    is MoneroTransactionSetInputAck -> MessageType_MoneroTransactionSetInputAck
    is MoneroTransactionInputViniRequest -> MessageType_MoneroTransactionInputViniRequest
    is MoneroTransactionInputViniAck -> MessageType_MoneroTransactionInputViniAck
    is MoneroTransactionAllInputsSetRequest -> MessageType_MoneroTransactionAllInputsSetRequest
    is MoneroTransactionAllInputsSetAck -> MessageType_MoneroTransactionAllInputsSetAck
    is MoneroTransactionSetOutputRequest -> MessageType_MoneroTransactionSetOutputRequest
    is MoneroTransactionSetOutputAck -> MessageType_MoneroTransactionSetOutputAck
    is MoneroTransactionAllOutSetRequest -> MessageType_MoneroTransactionAllOutSetRequest
    is MoneroTransactionAllOutSetAck -> MessageType_MoneroTransactionAllOutSetAck
    is MoneroTransactionSignInputRequest -> MessageType_MoneroTransactionSignInputRequest
    is MoneroTransactionSignInputAck -> MessageType_MoneroTransactionSignInputAck
    is MoneroTransactionFinalRequest -> MessageType_MoneroTransactionFinalRequest
    is MoneroTransactionFinalAck -> MessageType_MoneroTransactionFinalAck
    is MoneroKeyImageExportInitRequest -> MessageType_MoneroKeyImageExportInitRequest
    is MoneroKeyImageExportInitAck -> MessageType_MoneroKeyImageExportInitAck
    is MoneroKeyImageSyncStepRequest -> MessageType_MoneroKeyImageSyncStepRequest
    is MoneroKeyImageSyncStepAck -> MessageType_MoneroKeyImageSyncStepAck
    is MoneroKeyImageSyncFinalRequest -> MessageType_MoneroKeyImageSyncFinalRequest
    is MoneroKeyImageSyncFinalAck -> MessageType_MoneroKeyImageSyncFinalAck
    is MoneroGetAddress -> MessageType_MoneroGetAddress
    is MoneroAddress -> MessageType_MoneroAddress
    is MoneroGetWatchKey -> MessageType_MoneroGetWatchKey
    is MoneroWatchKey -> MessageType_MoneroWatchKey
    is DebugMoneroDiagRequest -> MessageType_DebugMoneroDiagRequest
    is DebugMoneroDiagAck -> MessageType_DebugMoneroDiagAck
    is MoneroGetTxKeyRequest -> MessageType_MoneroGetTxKeyRequest
    is MoneroGetTxKeyAck -> MessageType_MoneroGetTxKeyAck
    is MoneroLiveRefreshStartRequest -> MessageType_MoneroLiveRefreshStartRequest
    is MoneroLiveRefreshStartAck -> MessageType_MoneroLiveRefreshStartAck
    is MoneroLiveRefreshStepRequest -> MessageType_MoneroLiveRefreshStepRequest
    is MoneroLiveRefreshStepAck -> MessageType_MoneroLiveRefreshStepAck
    is MoneroLiveRefreshFinalRequest -> MessageType_MoneroLiveRefreshFinalRequest
    is MoneroLiveRefreshFinalAck -> MessageType_MoneroLiveRefreshFinalAck
    is EosGetPublicKey -> MessageType_EosGetPublicKey
    is EosPublicKey -> MessageType_EosPublicKey
    is EosSignTx -> MessageType_EosSignTx
    is EosTxActionRequest -> MessageType_EosTxActionRequest
    is EosTxActionAck -> MessageType_EosTxActionAck
    is EosSignedTx -> MessageType_EosSignedTx
    is BinanceGetAddress -> MessageType_BinanceGetAddress
    is BinanceAddress -> MessageType_BinanceAddress
    is BinanceGetPublicKey -> MessageType_BinanceGetPublicKey
    is BinancePublicKey -> MessageType_BinancePublicKey
    is BinanceSignTx -> MessageType_BinanceSignTx
    is BinanceTxRequest -> MessageType_BinanceTxRequest
    is BinanceTransferMsg -> MessageType_BinanceTransferMsg
    is BinanceOrderMsg -> MessageType_BinanceOrderMsg
    is BinanceCancelMsg -> MessageType_BinanceCancelMsg
    is BinanceSignedTx -> MessageType_BinanceSignedTx
    is WebAuthnListResidentCredentials -> MessageType_WebAuthnListResidentCredentials
    is WebAuthnCredentials -> MessageType_WebAuthnCredentials
    is WebAuthnAddResidentCredential -> MessageType_WebAuthnAddResidentCredential
    is WebAuthnRemoveResidentCredential -> MessageType_WebAuthnRemoveResidentCredential
    is SolanaGetPublicKey -> MessageType_SolanaGetPublicKey
    is SolanaPublicKey -> MessageType_SolanaPublicKey
    is SolanaGetAddress -> MessageType_SolanaGetAddress
    is SolanaAddress -> MessageType_SolanaAddress
    is SolanaSignTx -> MessageType_SolanaSignTx
    is SolanaTxSignature -> MessageType_SolanaTxSignature
    else -> error("Invalid message, not found in trezor")
}
