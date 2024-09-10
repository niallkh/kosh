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
import okio.Buffer
import okio.ByteString
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
            nonce = transaction.nonce.toBigInteger().toRlp.bytes,
            value_ = transaction.value.toRlp.bytes,
            max_priority_fee = transaction.maxPriorityFeePerGas.toRlp.bytes,
            gas_limit = transaction.gasLimit.toBigInteger().toRlp.bytes,
            max_gas_fee = transaction.maxFeePerGas.toRlp.bytes,
            data_length = transaction.data.size,
            data_initial_chunk = if (transaction.data.size > 0)
                data.readByteString(min(data.size, 1024))
            else null,
            definitions = EthereumDefinitions(
                encoded_network = networkDefinition,
                encoded_token = tokenDefinition,
            ),
            chunkify = true,
        )
    ).expect<EthereumTxRequest>()

    while (true) {
        val requestedDataLength = ethereumTxRequest.data_length
        if (requestedDataLength != null) {
            ethereumTxRequest = exchange(
                EthereumTxAck(
                    data_chunk = data.readByteString(requestedDataLength.toLong())
                )
            ).expect<EthereumTxRequest>()
        } else {
            return Buffer().apply {
                with(ethereumTxRequest) {
                    write(signature_r!!)
                    write(signature_s!!)
                    writeByte(signature_v!! + 27)
                }
            }.readByteString()
        }
    }
}
