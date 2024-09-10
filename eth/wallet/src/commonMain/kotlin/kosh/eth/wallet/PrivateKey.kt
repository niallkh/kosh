package kosh.eth.wallet

import okio.ByteString
import okio.ByteString.Companion.decodeHex
import kotlin.jvm.JvmInline

@JvmInline
public value class PrivateKey private constructor(internal val key: ByteString) {

    public companion object {
        public operator fun invoke(key: ByteString): PrivateKey {
            check(key.size == 32)
            return PrivateKey(key)
        }

        public operator fun invoke(key: String): PrivateKey =
            invoke(key.removePrefix("0x").decodeHex())
    }
}
