package kosh.eth.wallet

import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.wallet.transaction.Transaction
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.hexToByteString
import kotlin.test.Test
import kotlin.test.assertEquals

class Eip1559TransactionTest {

    @Test
    fun test() {
        val type1559 = Transaction.Type1559(
            chainId = 1559uL,
            nonce = 0u,
            gasLimit = 30000u,
            to = "627306090abaB3A6e1400e9345bC60c78a8BEf57".hexToByteString(),
            value = BigInteger(123),
            maxPriorityFeePerGas = BigInteger(5678),
            maxFeePerGas = BigInteger(1100000),
            data = ByteString()
        )

        val signature = Wallet.signTransaction(
            privateKey = PrivateKey("0x4646464646464646464646464646464646464646464646464646464646464646"),
            transaction = type1559,
        )

        assertEquals(
            "02f8698206178082162e8310c8e082753094627306090abab3a6e1400e9345bc60c78a8bef577b80c001a0d1f9ee3bdde4d4e0792c7089b84059fb28e17f494556d8a775450b1dd6c318a1a038bd3e2fb9e018528e0a41f57c7a32a8d23b2693e0451aa6ef4519b234466e7f".hexToByteString(),
            signature.data
        )
    }
}
