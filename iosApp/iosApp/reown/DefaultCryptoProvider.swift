import Foundation
import ReownWalletKit
import App

struct DefaultCryptoProvider: CryptoProvider {

    public func recoverPubKey(
        signature: EthereumSignature,
        message: Data
    ) throws -> Data {
        return RecoverPubKeyKt.recoverPubKey(
            signature: signature.serialized,
            message: message
        )
    }

    public func keccak256(_ data: Data) -> Data {
        return Keccak256Kt.keccak256(data: data)
    }
}
