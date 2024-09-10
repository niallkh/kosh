package kosh.eth.wallet

import org.kotlincrypto.SecureRandom

public fun ByteArray.fillSecureRandom(): ByteArray =
    this.apply { SecureRandom().nextBytesCopyTo(this) }
