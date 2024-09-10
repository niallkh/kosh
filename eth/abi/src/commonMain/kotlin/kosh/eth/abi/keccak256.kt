package kosh.eth.abi

import okio.ByteString
import okio.ByteString.Companion.toByteString
import org.kotlincrypto.hash.sha3.Keccak256

public fun ByteString.keccak256(): ByteString = Keccak256().digest(toByteArray()).toByteString()
