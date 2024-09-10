package kosh.eth.proposals.eip55

import kosh.eth.abi.Value
import kosh.eth.abi.keccak256
import okio.ByteString
import okio.ByteString.Companion.encodeUtf8

public fun ByteString.eip55(): String {
    require(size == 20)
    val hex = hex()
    val hashedHex = hex.encodeUtf8().keccak256().hex()
    return buildString(42) {
        append("0x")
        for (i in hex.indices) {
            val char = hex[i]
            val hashedChar = hashedHex[i]
            append(
                when {
                    char in '0'..'9' -> char
                    hashedChar in '0'..'7' -> char
                    else -> char.uppercaseChar()
                }
            )
        }
    }
}

public fun Value.Address.eip55(): String = value.eip55()
