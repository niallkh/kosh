package kosh.libs.trezor.cmds

import com.ionspin.kotlin.bignum.integer.Sign
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
import kosh.eth.abi.Value
import kosh.eth.abi.eip712.Eip712
import kosh.eth.abi.eip712.Eip712Type
import kosh.eth.abi.eip712.toValue
import kosh.eth.abi.padding
import kosh.libs.trezor.TrezorManager
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.encodeToByteString
import kotlinx.io.readByteString

suspend fun TrezorManager.Connection.signTypedMessage(
    derivationPath: List<UInt>,
    eip712: Eip712,
    networkDefinition: ByteString?,
    tokenDefinition: ByteString?,
): Pair<ByteString, String> {
    var response = exchange(
        EthereumSignTypedData(
            address_n = derivationPath.map { it.toInt() },
            primary_type = eip712.primaryType.name,
            metamask_v4_compat = true,
            definitions = EthereumDefinitions(
                encoded_network = networkDefinition?.toOkio(),
                encoded_token = tokenDefinition?.toOkio(),
            )
        )
    )

    while (true) {
        response.expectOrNull<EthereumTypedDataStructRequest>()?.let { request ->
            val parameters = eip712.types[Eip712Type.Tuple(request.name)]
                ?: eip712.types[Eip712Type.Tuple.Domain]
                ?: error("Smth went wrong, type not present: ${request.name}")

            val members = parameters.map { parameter ->
                EthereumStructMember(
                    name = checkNotNull(parameter.name),
                    type = eip712.toFieldType(parameter.type),
                )
            }

            response = exchange(EthereumTypedDataStructAck(members = members))
        }

        response.expectOrNull<EthereumTypedDataValueRequest>()?.let { request ->
            val value = if (request.member_path.first() == 0) {
                eip712.toByteString(
                    type = Eip712Type.Tuple.Domain,
                    value = eip712.domain.toValue(),
                    memberPath = request.member_path.drop(1)
                )
            } else {
                eip712.toByteString(
                    type = eip712.primaryType,
                    value = eip712.message,
                    memberPath = request.member_path.drop(1),
                )
            }
            response = exchange(EthereumTypedDataValueAck(value_ = value.toOkio()))
        }

        response.expectOrNull<EthereumTypedDataSignature>()?.let {
            return it.signature.toIo() to it.address
        }
    }
}

private fun toDataType(type: Eip712Type): EthereumDataType = when (type) {
    is Eip712Type.UInt -> EthereumDataType.UINT
    is Eip712Type.Int -> EthereumDataType.INT
    is Eip712Type.Bool -> EthereumDataType.BOOL
    is Eip712Type.FixedBytes -> EthereumDataType.BYTES
    is Eip712Type.Address -> EthereumDataType.ADDRESS
    is Eip712Type.DynamicBytes -> EthereumDataType.BYTES
    is Eip712Type.DynamicString -> EthereumDataType.STRING
    is Eip712Type.FixedArray -> EthereumDataType.ARRAY
    is Eip712Type.DynamicArray -> EthereumDataType.ARRAY
    is Eip712Type.Tuple -> EthereumDataType.STRUCT
}

private fun Eip712.toSize(type: Eip712Type): UInt? = when (type) {
    is Eip712Type.UInt -> type.bitSize / 8u
    is Eip712Type.Int -> type.bitSize / 8u
    is Eip712Type.Bool -> null
    is Eip712Type.FixedBytes -> type.byteSize
    is Eip712Type.Address -> null
    is Eip712Type.DynamicBytes -> null
    is Eip712Type.DynamicString -> null
    is Eip712Type.FixedArray -> type.size
    is Eip712Type.DynamicArray -> null
    is Eip712Type.Tuple -> types.getValue(type).size.toUInt()
}

private fun Eip712Type.toStructName() = when (this) {
    is Eip712Type.UInt,
    is Eip712Type.Int,
    is Eip712Type.Bool,
    is Eip712Type.FixedBytes,
    is Eip712Type.Address,
    is Eip712Type.DynamicBytes,
    is Eip712Type.DynamicString,
    is Eip712Type.FixedArray,
    is Eip712Type.DynamicArray,
    -> null

    is Eip712Type.Tuple -> name
}

private fun Eip712.toFieldType(type: Eip712Type): EthereumFieldType {
    val entryType = when (type) {
        Eip712Type.Address,
        Eip712Type.Bool,
        Eip712Type.DynamicBytes,
        Eip712Type.DynamicString,
        is Eip712Type.FixedBytes,
        is Eip712Type.Int,
        is Eip712Type.Tuple,
        is Eip712Type.UInt,
        -> null

        is Eip712Type.FixedArray -> toFieldType(type.type)
        is Eip712Type.DynamicArray -> toFieldType(type.type)
    }

    return EthereumFieldType(
        data_type = toDataType(type),
        size = toSize(type)?.toInt(),
        entry_type = entryType,
        struct_name = type.toStructName()
    )
}

private fun Eip712.toByteString(
    type: Eip712Type,
    value: Value,
    memberPath: List<Int>,
): ByteString = when (type) {
    is Eip712Type.UInt -> encodeUInt(type.bitSize, value)
    is Eip712Type.Int -> encodeInt(type.bitSize, value)
    is Eip712Type.Bool -> encodeBool(value)
    is Eip712Type.FixedBytes -> (value as Value.Bytes).value
    is Eip712Type.Address -> (value as Value.Address).value
    is Eip712Type.DynamicBytes -> (value as Value.Bytes).value
    is Eip712Type.DynamicString -> (value as Value.String).value.encodeToByteString()

    is Eip712Type.FixedArray -> toByteString(
        type = type.type,
        value = (value as Value.Array<*>)[memberPath.first()],
        memberPath = memberPath.drop(1)
    )

    is Eip712Type.DynamicArray -> toByteString(
        type = type.type,
        value = (value as Value.Array<*>)[memberPath.first()],
        memberPath = memberPath.drop(1)
    )

    is Eip712Type.Tuple -> {
        val tuple = types.keys.toList()[memberPath.first()]
        toByteString(
            type = tuple,
            value = (value as Value.Tuple).getValue(tuple.name),
            memberPath = memberPath.drop(1)
        )
    }
}

internal fun encodeUInt(bitSize: UInt = 256u, value: Value): ByteString {
    require(value is Value.BigNumber)
    require(bitSize in 8u..256u && bitSize % 8u == 0u)
    require(value.value.getSign() != Sign.NEGATIVE)
    val byteArray = value.value.toByteArray()
    require(byteArray.size.toUInt() <= bitSize / 8u)

    return Buffer().run {
        padding((bitSize / 8u).toInt() - byteArray.size)
        write(byteArray)
        readByteString()
    }
}

internal fun encodeInt(bitSize: UInt = 256u, value: Value): ByteString {
    require(value is Value.BigNumber)
    require(bitSize in 8u..256u && bitSize % 8u == 0u)
    val byteArray = value.value.toTwosComplementByteArray()
    require(byteArray.size.toUInt() <= bitSize / 8u)

    return Buffer().run {
        padding((bitSize / 8u).toInt() - byteArray.size)
        write(byteArray)
        readByteString()
    }
}

internal fun encodeBool(value: Value): ByteString {
    require(value is Value.Bool)

    return Buffer().run {
        padding(31)
        writeByte(if (value.value) 1 else 0)
        readByteString()
    }
}
