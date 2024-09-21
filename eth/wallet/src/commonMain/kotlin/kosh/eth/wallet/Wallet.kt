package kosh.eth.wallet

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.toBigInteger
import kosh.eth.abi.Value
import kosh.eth.abi.eip712.Eip712
import kosh.eth.abi.eip712.Eip712Type
import kosh.eth.abi.eip712.structHash
import kosh.eth.abi.eip712.toValue
import kosh.eth.abi.keccak256
import kosh.eth.abi.rlp.toRlp
import kosh.eth.wallet.transaction.Signature
import kosh.eth.wallet.transaction.SignatureData
import kosh.eth.wallet.transaction.Transaction
import kosh.eth.wallet.transaction.concat
import kosh.eth.wallet.transaction.encode
import kosh.eth.wallet.transaction.vbg
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.hexToByteString
import kotlinx.io.readByteString
import kotlinx.io.write
import kotlinx.io.writeString

public object Wallet {

    public fun generatePrivateKey(): ByteArray = ByteArray(32).fillSecureRandom()

    public fun address(
        privkey: PrivateKey,
    ): Value.Address = Secp256k1.publicKey(privkey.key)
        .substring(1, 65)
        .keccak256()
        .substring(12, 32)
        .let(Value.Address::invoke)

    public fun verify(
        privkey: PrivateKey,
        data: ByteString,
        messageHash: ByteString,
    ): Boolean = Secp256k1.verify(
        signature = data,
        messageHash = messageHash,
        publicKey = Secp256k1.publicKey(privkey.key)
    )

    public fun recover(
        signature: ByteString,
        messageHash: ByteString,
    ): Value.Address = Secp256k1.recover(
        signature = signature.substring(0, 64),
        messageHash = messageHash,
        recId = signature[64].toInt() - 27
    )
        .substring(1, 65)
        .keccak256()
        .substring(12, 32)
        .let(Value.Address::invoke)

    public fun personalSign(
        privateKey: PrivateKey,
        message: ByteString,
    ): Signature = personalHash(message).let { hash ->
        Signature(
            data = sign(privateKey, hash).concat(),
            messageHash = hash,
        )
    }

    public fun personalHash(message: ByteString): ByteString = Buffer().run {
        writeString("\u0019Ethereum Signed Message:\n")
        writeString(message.size.toString())
        write(message)

        readByteString()
    }.keccak256()

    public fun signTypeData(
        privateKey: PrivateKey,
        eip712: Eip712,
    ): Signature = typeDataHash(eip712).let { hash ->
        Signature(
            data = sign(privateKey, hash).concat(),
            messageHash = hash,
        )
    }

    public fun typeDataHash(eip712: Eip712): ByteString = Buffer().run {
        write("1901".hexToByteString())
        write(eip712.types.structHash(eip712.primaryType, eip712.message))
        write(eip712.types.structHash(Eip712Type.Tuple.Domain, eip712.domain.toValue()))
        readByteString()
    }.keccak256()

    public fun signTransaction(
        privateKey: PrivateKey,
        transaction: Transaction,
    ): Signature = when (transaction) {
        is Transaction.Legacy -> signLegacyTransaction(transaction, privateKey)
        is Transaction.Type1559 -> sign1559Transaction(transaction, privateKey)
    }

    public fun signLegacyTransaction(
        transaction: Transaction.Legacy,
        privateKey: PrivateKey,
    ): Signature {
        val chainId = BigInteger.fromULong(transaction.chainId)
        val signature = SignatureData(v = chainId.toRlp.bytes)
        val hash = transaction.encode(signature = signature).keccak256()
        val signatureData = sign(
            privkey = privateKey,
            messageHash = hash
        )

        val v = signatureData.vbg() + chainId.shl(1) + 8.toBigInteger()

        return Signature(
            data = transaction.encode(signature = signatureData.copy(v = v.toRlp.bytes)),
            messageHash = hash,
        )
    }

    public fun sign1559Transaction(
        transaction: Transaction.Type1559,
        privateKey: PrivateKey,
    ): Signature {
        val hash = transaction.encode(signatureData = null).keccak256()

        val signatureData = sign(
            privkey = privateKey,
            messageHash = hash
        )

        return encode1559Transaction(transaction, signatureData.concat())
    }

    public fun encode1559Transaction(
        transaction: Transaction.Type1559,
        signature: ByteString,
    ): Signature {
        val hash = transaction.encode(signatureData = null).keccak256()

        return Signature(
            data = transaction.encode(
                signatureData = SignatureData(
                    r = signature.substring(0, 32),
                    s = signature.substring(32, 64),
                    v = signature.substring(64, 65)
                )
            ),
            messageHash = hash,
        )
    }
}
