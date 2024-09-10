package kosh.libs.trezor.cmds

import com.ionspin.kotlin.bignum.integer.Sign
import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.ionspin.kotlin.bignum.integer.util.toTwosComplementByteArray
import com.satoshilabs.trezor.lib.protobuf.EthereumDefinitions
import com.satoshilabs.trezor.lib.protobuf.EthereumSignTypedData
import com.satoshilabs.trezor.lib.protobuf.EthereumTypedDataSignature
import com.satoshilabs.trezor.lib.protobuf.EthereumTypedDataStructAck
import com.satoshilabs.trezor.lib.protobuf.EthereumTypedDataStructAck.EthereumDataType
import com.satoshilabs.trezor.lib.protobuf.EthereumTypedDataStructAck.EthereumFieldType
import com.satoshilabs.trezor.lib.protobuf.EthereumTypedDataStructAck.EthereumStructMember
import com.satoshilabs.trezor.lib.protobuf.EthereumTypedDataStructRequest
import com.satoshilabs.trezor.lib.protobuf.EthereumTypedDataValueAck
import com.satoshilabs.trezor.lib.protobuf.EthereumTypedDataValueRequest
import kosh.eth.abi.Eip712
import kosh.eth.abi.Type
import kosh.eth.abi.Value
import kosh.eth.abi.coder.padding
import kosh.libs.trezor.TrezorManager
import okio.Buffer
import okio.ByteString
import okio.ByteString.Companion.encodeUtf8

suspend fun TrezorManager.Connection.signTypedMessage(
    derivationPath: List<UInt>,
    eip712: Eip712,
    networkDefinition: ByteString?,
    tokenDefinition: ByteString?,
): Pair<ByteString, String> {
    val primaryType = eip712.message.type.name!!

    var response = exchange(
        EthereumSignTypedData(
            address_n = derivationPath.map { it.toInt() },
            primary_type = primaryType,
            metamask_v4_compat = true,
            definitions = EthereumDefinitions(
                encoded_network = networkDefinition,
                encoded_token = tokenDefinition,
            )
        )
    )

    while (true) {
        response.expectOrNull<EthereumTypedDataStructRequest>()?.let { request ->
            val tuple = eip712.message.type.findType(request.name) ?: eip712.domain.type

            val members = tuple.map { parameter ->
                EthereumStructMember(
                    name = checkNotNull(parameter.name),
                    type = EthereumFieldType(
                        data_type = parameter.type.toDataType(),
                        size = parameter.type.toSize(),
                        entry_type = parameter.type.toEntryType(),
                        struct_name = parameter.type.toStructName(),
                    ),
                )
            }

            response = exchange(EthereumTypedDataStructAck(members = members))
        }

        response.expectOrNull<EthereumTypedDataValueRequest>()?.let { request ->
            var index = request.member_path.first()
            val value = if (index == 0) {
                eip712.domain.type.toByteString(eip712.domain.value, request.member_path.drop(1))
            } else {
                index = request.member_path.drop(1).first()
                eip712.message.type[index].type.toByteString(
                    eip712.message.value[index],
                    request.member_path.drop(2)
                )
            }
            response = exchange(EthereumTypedDataValueAck(value_ = value))
        }

        response.expectOrNull<EthereumTypedDataSignature>()?.let {
            return it.signature to it.address
        }
    }
}

private fun Type.Tuple.findType(name: String): Type.Tuple? {
    if (name == this.name) return this

    for (parameter in this) {
        val type = (parameter.type as? Type.Tuple)?.findType(name)
        if (type != null) return type
    }
    return null
}

private fun Type.toDataType(): EthereumDataType = when (this) {
    is Type.UInt -> EthereumDataType.UINT
    is Type.Int -> EthereumDataType.INT
    is Type.Bool -> EthereumDataType.BOOL
    is Type.FixedBytes -> EthereumDataType.BYTES
    is Type.Address -> EthereumDataType.ADDRESS
    is Type.DynamicBytes -> EthereumDataType.BYTES
    is Type.DynamicString -> EthereumDataType.STRING
    is Type.FixedArray -> EthereumDataType.ARRAY
    is Type.DynamicArray -> EthereumDataType.ARRAY
    is Type.Tuple -> EthereumDataType.STRUCT
    is Type.Function -> error("Function type not supported by Trezor")
}

private fun Type.toSize() = when (this) {
    is Type.UInt -> bitSize / 8u
    is Type.Int -> bitSize / 8u
    is Type.Bool -> null
    is Type.FixedBytes -> byteSize
    is Type.Address -> null
    is Type.DynamicBytes -> null
    is Type.DynamicString -> null
    is Type.FixedArray -> size
    is Type.DynamicArray -> null
    is Type.Tuple -> size.toUInt()
    is Type.Function -> error("Function type isn't supported by trezor")
}?.toInt()

private fun Type.toEntryType(): EthereumFieldType? = when (this) {
    is Type.UInt -> null
    is Type.Int -> null
    is Type.Bool -> null
    is Type.FixedBytes -> null
    is Type.Address -> null
    is Type.DynamicBytes -> null
    is Type.DynamicString -> null
    is Type.FixedArray -> EthereumFieldType(
        data_type = type.toDataType(),
        size = type.toSize(),
        entry_type = type.toEntryType(),
        struct_name = type.toStructName()
    )

    is Type.DynamicArray -> EthereumFieldType(
        data_type = type.toDataType(),
        size = type.toSize(),
        entry_type = type.toEntryType(),
        struct_name = type.toStructName()
    )

    is Type.Tuple -> null
    is Type.Function -> error("Function type isn't supported by trezor")
}

private fun Type.toStructName() = when (this) {
    is Type.UInt -> null
    is Type.Int -> null
    is Type.Bool -> null
    is Type.FixedBytes -> null
    is Type.Address -> null
    is Type.DynamicBytes -> null
    is Type.DynamicString -> null
    is Type.FixedArray -> null
    is Type.DynamicArray -> null
    is Type.Tuple -> name
    is Type.Function -> error("Function type isn't supported by trezor")
}

private fun Type.toByteString(value: Value, memberPath: List<Int>): ByteString = when (this) {
    is Type.UInt -> toByteString(value as Value.BigNumber)
    is Type.Int -> toByteString(value as Value.BigNumber)
    is Type.Bool -> if ((value as Value.Bool).value)
        Type.UInt.UInt256.toByteString(Value.BigNumber(1.toBigInteger()))
    else
        Type.UInt.UInt256.toByteString(Value.BigNumber(0.toBigInteger()))

    is Type.FixedBytes -> (value as Value.Bytes).value
    is Type.Address -> (value as Value.Address).value
    is Type.DynamicBytes -> (value as Value.Bytes).value
    is Type.DynamicString -> (value as Value.String).value.encodeUtf8()

    is Type.FixedArray -> type.toByteString(
        (value as Value.Array<*>)[memberPath.first()],
        memberPath.drop(1)
    )

    is Type.DynamicArray -> type.toByteString(
        (value as Value.Array<*>)[memberPath.first()],
        memberPath.drop(1)
    )

    is Type.Tuple -> get(memberPath.first()).type.toByteString(
        (value as Value.Tuple)[memberPath.first()],
        memberPath.drop(1)
    )

    is Type.Function -> error("Function type isn't supported by trezor")
}

private fun Type.UInt.toByteString(value: Value.BigNumber) = Buffer().run {
    require(bitSize in 8u..256u && bitSize % 8u == 0u)
    require(value.value.getSign() != Sign.NEGATIVE)
    val bytes = value.value.toByteArray()
    val byteSize = (bitSize / 8u).toInt()
    require(byteSize >= bytes.size)

    padding(byteSize - bytes.size)
    write(bytes)
    readByteString()
}

private fun Type.Int.toByteString(value: Value.BigNumber) = Buffer().run {
    require(bitSize in 8u..256u && bitSize % 8u == 0u)
    val bytes = value.value.toTwosComplementByteArray()
    val byteSize = (bitSize / 8u).toInt()
    require(byteSize >= bytes.size)

    padding(byteSize - bytes.size)
    write(bytes)
    readByteString()
}
