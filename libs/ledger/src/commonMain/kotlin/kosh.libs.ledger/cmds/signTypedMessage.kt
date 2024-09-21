package kosh.libs.ledger.cmds

import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.ionspin.kotlin.bignum.integer.util.toTwosComplementByteArray
import kosh.eth.abi.Value
import kosh.eth.abi.eip712.Eip712
import kosh.eth.abi.eip712.Eip712Type
import kosh.eth.abi.eip712.toValue
import kosh.libs.ledger.LedgerManager
import kosh.libs.ledger.StatusWord
import kosh.libs.ledger.cmds.Eip712Filters.Format
import kosh.libs.ledger.exchange
import kosh.libs.ledger.ledgerAPDU
import kosh.libs.ledger.toInt
import kotlinx.io.Buffer
import kotlinx.io.Sink
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.encodeToByteString
import kotlinx.io.bytestring.hexToByteString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import kotlinx.io.readByteString
import kotlinx.io.write
import kotlinx.io.writeString

suspend fun LedgerManager.Connection.signTypedMessage(
    derivationPath: List<UInt>,
    eip712: Eip712,
    parameters: SignTypedMessageParameters?,
): ByteString {

    eip712.types.entries.sortedBy { it.key.name }.forEach { (tuple, params) ->
        exchange(structName(tuple.name)) { sw ->
            sw.expectToBe(StatusWord.OK)
        }

        params.forEach { param ->
            exchange(structField(param.name, param.type)) { sw ->
                sw.expectToBe(StatusWord.OK)
            }
        }
    }

    if (parameters?.filters != null) {
        exchange(filterActivation()) { sw ->
            sw.expectToBe(StatusWord.OK)
        }

        parameters.tokens.forEach {
            provideToken(it)
        }
    }

    sendMessage(
        eip712 = eip712,
        type = Eip712Type.Tuple.Domain,
        value = eip712.domain.toValue(),
        filters = null
    )

    if (parameters?.filters != null) {
        exchange(filterMessageInfo(parameters.filters)) { sw ->
            sw.expectToBe(StatusWord.OK)
        }
    }

    sendMessage(
        eip712 = eip712,
        type = eip712.primaryType,
        value = eip712.message,
        filters = parameters?.filters,
    )

    val response = exchange(ledgerAPDU(0xe0, 0x0c, 0x00, 0x01) {
        writeByte(derivationPath.size.toByte())
        derivationPath.forEach {
            writeInt(it.toInt())
        }
    }) { sw ->
        sw.expectToBe(StatusWord.OK)
        readByteString()
    }

    return Buffer().run {
        write(response, 1, 64)
        write(response, 0, 1)
        readByteString()
    }
}

private suspend fun LedgerManager.Connection.sendMessage(
    eip712: Eip712,
    type: Eip712Type.Tuple,
    value: Value.Tuple,
    filters: Eip712Filters?,
) {
    sendRoot(type.name)
    send(eip712, type, value, filters, listOf())
}

private fun structName(name: String) = ledgerAPDU(0xe0, 0x1a, 0x00, 0x00) {
    writeString(name)
}

private fun structField(
    name: String,
    type: Eip712Type,
) = ledgerAPDU(0xe0, 0x1a, 0x00, 0xff) {
    writeByte((type.desc() or type.key()).toByte())
    if (type is Eip712Type.Tuple) {
        writeByte(type.name.length.toByte())
        writeString(type.name)
    }
    type.typeSize()?.let {
        writeByte(it.toByte())
    }
    type.arrayLevelCount()?.let {
        writeByte(it.toByte())
    }
    type.arrayLevels(this)
    writeByte(name.length.toByte())
    writeString(name)
}

private suspend fun LedgerManager.Connection.sendRoot(name: String) {
    exchange(ledgerAPDU(0xe0, 0x1c, 0x00, 0x00) {
        writeString(name)
    }) { sw ->
        sw.expectToBe(StatusWord.OK)
    }
}

private suspend fun LedgerManager.Connection.sendArray(size: Int) {
    exchange(ledgerAPDU(0xe0, 0x1c, 0x00, 0x0f) {
        writeByte(size.toByte())
    }) { sw ->
        sw.expectToBe(StatusWord.OK)
    }
}

private suspend fun LedgerManager.Connection.sendField(
    bytes: ByteString,
    filters: Eip712Filters?,
    path: List<String>,
) {
    if (filters != null) {
        val joinedPath = path.joinToString(".")

        filters.fields.find { it.path == joinedPath }?.let { filter ->
            val apdu = when (filter.format) {
                Format.DATETIME -> filterDateTime(filter)
                Format.TOKEN -> filterToken(filter, filter.coinRef!!)
                Format.AMOUNT -> filterAmount(filter, filter.coinRef!!)
                Format.RAW -> filterRaw(filter)
                null -> filterRaw(filter)
            }

            exchange(apdu) { sw ->
                sw.expectToBe(StatusWord.OK)
            }
        }
    }

    val messageBuffer = Buffer().apply { write(bytes) }

    var chunk = messageBuffer.readChunk(254)
    val p1 = messageBuffer.exhausted().not().toInt()
    exchange(ledgerAPDU(0xe0, 0x1c, p1, 0xff) {
        writeShort(bytes.size.toShort())
        write(chunk)
    }) { sw ->
        sw.expectToBe(StatusWord.OK)
    }

    while (!messageBuffer.exhausted()) {
        chunk = messageBuffer.readChunk(256)
        val p11 = messageBuffer.exhausted().not().toInt()
        exchange(ledgerAPDU(0xe0, 0x1c, p11, 0xff) {
            write(chunk)
        }) { sw ->
            sw.expectToBe(StatusWord.OK)
        }
    }
}

private fun filterActivation() = ledgerAPDU(0xe0, 0x1e, 0x00, 0x00)

private fun filterMessageInfo(
    eip712Filter: Eip712Filters,
) = ledgerAPDU(0xe0, 0x1e, 0x00, 0x0f) {
    writeByte(eip712Filter.contractName.label.length.toByte())
    writeString(eip712Filter.contractName.label)
    writeByte(eip712Filter.fields.size.toByte())
    val signature = eip712Filter.contractName.signature.hexToByteString()
    writeByte(signature.size.toByte())
    write(signature)
}

private fun filterRaw(
    filter: Eip712Filters.Filter,
) = ledgerAPDU(0xe0, 0x1e, 0x00, 0xff) {
    writeByte(filter.label.length.toByte())
    writeString(filter.label)
    val signature = filter.signature.hexToByteString()
    writeByte(signature.size.toByte())
    write(signature)
}

private fun filterDateTime(
    filter: Eip712Filters.Filter,
) = ledgerAPDU(0xe0, 0x1e, 0x00, 0xfc) {
    writeByte(filter.label.length.toByte())
    writeString(filter.label)
    val signature = filter.signature.hexToByteString()
    writeByte(signature.size.toByte())
    write(signature)
}

private fun filterToken(
    filter: Eip712Filters.Filter,
    tokenIndex: Int,
) = ledgerAPDU(0xe0, 0x1e, 0x00, 0xfd) {
    writeByte(tokenIndex.toByte())
    val signature = filter.signature.hexToByteString()
    writeByte(signature.size.toByte())
    write(signature)
}

private fun filterAmount(
    filter: Eip712Filters.Filter,
    tokenIndex: Int,
) = ledgerAPDU(0xe0, 0x1e, 0x00, 0xfe) {
    writeByte(filter.label.length.toByte())
    writeString(filter.label)
    writeByte(tokenIndex.toByte())
    val signature = filter.signature.hexToByteString()
    writeByte(signature.size.toByte())
    write(signature)
}

private fun Eip712Type.desc(): Int = when (this) {
    is Eip712Type.FixedBytes -> 1 shl 6
    is Eip712Type.Int -> 1 shl 6
    is Eip712Type.UInt -> 1 shl 6
    is Eip712Type.DynamicArray -> type.desc() or (1 shl 7)
    is Eip712Type.FixedArray -> type.desc() or (1 shl 7)
    Eip712Type.Address -> 0
    Eip712Type.Bool -> 0
    Eip712Type.DynamicBytes -> 0
    Eip712Type.DynamicString -> 0
    is Eip712Type.Tuple -> 0
}

private fun Eip712Type.key(): Int = when (this) {
    is Eip712Type.Tuple -> 0
    is Eip712Type.Int -> 1
    is Eip712Type.UInt -> 2
    Eip712Type.Address -> 3
    Eip712Type.Bool -> 4
    Eip712Type.DynamicString -> 5
    is Eip712Type.FixedBytes -> 6
    Eip712Type.DynamicBytes -> 7
    is Eip712Type.DynamicArray -> type.key()
    is Eip712Type.FixedArray -> type.key()
}

private fun Eip712Type.typeSize(): Int? = when (this) {
    is Eip712Type.Int -> bitSize.toInt() / 8
    is Eip712Type.UInt -> bitSize.toInt() / 8
    is Eip712Type.FixedBytes -> byteSize.toInt()
    is Eip712Type.Tuple -> null
    Eip712Type.Address -> null
    Eip712Type.Bool -> null
    Eip712Type.DynamicString -> null
    Eip712Type.DynamicBytes -> null
    is Eip712Type.DynamicArray -> type.typeSize()
    is Eip712Type.FixedArray -> type.typeSize()
}

private fun Eip712Type.arrayLevelCount(): Int? = when (this) {
    is Eip712Type.Int -> null
    is Eip712Type.UInt -> null
    is Eip712Type.FixedBytes -> null
    is Eip712Type.Tuple -> null
    Eip712Type.Address -> null
    Eip712Type.Bool -> null
    Eip712Type.DynamicString -> null
    Eip712Type.DynamicBytes -> null
    is Eip712Type.DynamicArray -> 1 + (type.arrayLevelCount() ?: 0)
    is Eip712Type.FixedArray -> 1 + (type.arrayLevelCount() ?: 0)
}

private fun Eip712Type.arrayLevels(sink: Sink): Unit = when (this) {
    is Eip712Type.Int -> Unit
    is Eip712Type.UInt -> Unit
    is Eip712Type.FixedBytes -> Unit
    is Eip712Type.Tuple -> Unit
    Eip712Type.Address -> Unit
    Eip712Type.Bool -> Unit
    Eip712Type.DynamicString -> Unit
    Eip712Type.DynamicBytes -> Unit
    is Eip712Type.DynamicArray -> {
        type.arrayLevels(sink)
        sink.writeByte(0)
    }

    is Eip712Type.FixedArray -> {
        type.arrayLevels(sink)
        sink.writeByte(1)
        sink.writeByte(size.toByte())
    }
}

private suspend fun LedgerManager.Connection.send(
    eip712: Eip712,
    type: Eip712Type,
    value: Value,
    filters: Eip712Filters?,
    path: List<String>,
): Unit = when (type) {
    is Eip712Type.UInt -> (value as Value.BigNumber).value
        .toByteArray().let { sendField(UnsafeByteStringOperations.wrapUnsafe(it), filters, path) }

    is Eip712Type.Int -> (value as Value.BigNumber).value
        .toTwosComplementByteArray()
        .let { sendField(UnsafeByteStringOperations.wrapUnsafe(it), filters, path) }

    is Eip712Type.Bool -> (value as Value.Bool).value.toInt().toBigInteger()
        .toByteArray().let { sendField(UnsafeByteStringOperations.wrapUnsafe(it), filters, path) }

    is Eip712Type.FixedBytes -> sendField((value as Value.Bytes).value, filters, path)
    is Eip712Type.Address -> sendField((value as Value.Address).value, filters, path)
    is Eip712Type.DynamicBytes -> sendField((value as Value.Bytes).value, filters, path)
    is Eip712Type.DynamicString -> sendField(
        (value as Value.String).value.encodeToByteString(),
        filters,
        path
    )

    is Eip712Type.FixedArray -> {
        sendArray(type.size.toInt())

        (value as Value.Array<*>).forEach { arrayValue ->
            send(eip712, type.type, arrayValue, filters, path + "[]")
        }
    }

    is Eip712Type.DynamicArray -> (value as Value.Array<*>).forEach { arrayValue ->
        send(eip712, type.type, arrayValue, filters, path + "[]")
    }

    is Eip712Type.Tuple -> {
        val tuple = value as Value.Tuple
        eip712.types.getValue(type).forEach { param ->
            send(eip712, param.type, tuple.getValue(param.name), filters, path + param.name)
        }
    }
}

fun Eip712Type.valueAt(eip712: Eip712, value: Value, path: List<String>): Value = when (this) {
    Eip712Type.Bool -> (value as Value.Bool).also { require(path.isEmpty()) }
    Eip712Type.Address -> (value as Value.Address).also { require(path.isEmpty()) }
    Eip712Type.DynamicBytes -> (value as Value.Bytes).also { require(path.isEmpty()) }
    Eip712Type.DynamicString -> (value as Value.String).also { require(path.isEmpty()) }
    is Eip712Type.FixedBytes -> (value as Value.Bytes).also { require(path.isEmpty()) }
    is Eip712Type.Int -> (value as Value.BigNumber).also { require(path.isEmpty()) }
    is Eip712Type.UInt -> (value as Value.BigNumber).also { require(path.isEmpty()) }

    is Eip712Type.DynamicArray -> {
        val index = path.first().toInt()
        type.valueAt(eip712, (value as Value.Array<*>)[index], path.drop(1))
    }

    is Eip712Type.FixedArray -> {
        val index = path.first().toInt()
        type.valueAt(eip712, (value as Value.Array<*>)[index], path.drop(1))
    }

    is Eip712Type.Tuple -> {
        val parameters = eip712.types.getValue(this)
        val index = parameters.indexOfFirst { it.name == path.first() }
        val tuple = value as Value.Tuple
        parameters[index].type.valueAt(eip712, tuple.values.toList()[index], path.drop(1))
    }
}

fun Eip712.at(type: Eip712Type, value: Value, path: List<String>): Value = when (type) {
    Eip712Type.Address,
    Eip712Type.Bool,
    Eip712Type.DynamicBytes,
    Eip712Type.DynamicString,
    is Eip712Type.FixedBytes,
    is Eip712Type.Int,
    is Eip712Type.UInt,
    -> value

    is Eip712Type.FixedArray -> {
        val index = path.first().toInt()
        at(type.type, (value as Value.Array<*>)[index], path.drop(1))
    }

    is Eip712Type.DynamicArray -> {
        val index = path.first().toInt()
        at(type.type, (value as Value.Array<*>)[index], path.drop(1))
    }

    is Eip712Type.Tuple -> {
        val name = path.first()
        val parameters = types.getValue(type)

        val param = parameters.first { it.name == name }
        val value1 = (value as Value.Tuple).getValue(name)
        at(param.type, value1, path.drop(1))
    }
}
