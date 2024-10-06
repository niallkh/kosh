package kosh.app.crypto

import kosh.eth.abi.keccak256
import kosh.eth.wallet.Wallet
import platform.Foundation.NSData

fun recoverPubKey(
    signature: NSData,
    message: NSData,
): NSData = Wallet.recoverPubKey(
    signature = signature.toByteString(),
    messageHash = message.toByteString().keccak256(),
).toData()
