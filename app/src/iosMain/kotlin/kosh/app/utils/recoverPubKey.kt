package kosh.app.utils

import kosh.eth.abi.keccak256
import kosh.eth.wallet.Wallet
import platform.Foundation.NSData

public fun recoverPubKey(
    signature: NSData,
    message: NSData,
): NSData = Wallet.recoverPubKey(
    signature = signature.toByteString(),
    messageHash = message.toByteString().keccak256(),
).toData()
