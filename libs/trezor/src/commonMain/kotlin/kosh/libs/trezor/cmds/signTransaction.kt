package kosh.libs.trezor.cmds

import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.satoshilabs.trezor.lib.protobuf.EthereumDefinitions
import com.satoshilabs.trezor.lib.protobuf.EthereumSignTxEIP1559
import com.satoshilabs.trezor.lib.protobuf.EthereumTxAck
import com.satoshilabs.trezor.lib.protobuf.EthereumTxRequest
import kosh.eth.abi.rlp.toRlp
import kosh.eth.proposals.eip55.eip55
import kosh.eth.wallet.transaction.Transaction
import kosh.libs.trezor.TrezorManager
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString
import kotlinx.io.write
import kotlin.math.min

suspend fun TrezorManager.Connection.signTransaction(
    derivationPath: List<UInt>,
    transaction: Transaction.Type1559,
    networkDefinition: ByteString?,
    tokenDefinition: ByteString?,
): ByteString {
    val data = Buffer().apply { write(transaction.data) }

    var ethereumTxRequest = exchange(
        EthereumSignTxEIP1559(
            chain_id = transaction.chainId.toLong(),
            address_n = derivationPath.map { it.toInt() },
            to = transaction.to?.eip55(),
            nonce = transaction.nonce.toBigInteger().toRlp.bytes.toOkio(),
            value_ = transaction.value.toRlp.bytes.toOkio(),
            max_priority_fee = transaction.maxPriorityFeePerGas.toRlp.bytes.toOkio(),
            gas_limit = transaction.gasLimit.toBigInteger().toRlp.bytes.toOkio(),
            max_gas_fee = transaction.maxFeePerGas.toRlp.bytes.toOkio(),
            data_length = transaction.data.size,
            data_initial_chunk = if (transaction.data.size > 0)
                data.readByteString(min(data.size.toInt(), 1024)).toOkio()
            else null,
            definitions = EthereumDefinitions(
                encoded_network = networkDefinition?.toOkio(),
                encoded_token = tokenDefinition?.toOkio(),
            ),
            chunkify = true,
        )
    ).expect<EthereumTxRequest>()

    while (true) {
        val requestedDataLength = ethereumTxRequest.data_length
        if (requestedDataLength != null) {
            ethereumTxRequest = exchange(
                EthereumTxAck(
                    data_chunk = data.readByteString(requestedDataLength).toOkio()
                )
            ).expect<EthereumTxRequest>()
        } else {
            return Buffer().apply {
                with(ethereumTxRequest) {
                    write(signature_r!!.toIo())
                    write(signature_s!!.toIo())
                    writeByte((signature_v!! + 27).toByte())
                }
            }.readByteString()
        }
    }
}
