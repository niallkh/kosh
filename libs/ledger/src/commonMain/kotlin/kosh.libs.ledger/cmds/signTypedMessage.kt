package kosh.libs.ledger.cmds

import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.ionspin.kotlin.bignum.integer.util.toTwosComplementByteArray
import kosh.eth.abi.Eip712V2
import kosh.eth.abi.Type
import kosh.eth.abi.Value
import kosh.eth.abi.toTuple
import kosh.libs.ledger.LedgerManager
import kosh.libs.ledger.StatusWord
import kosh.libs.ledger.cmds.Eip712Filters.Format
import kosh.libs.ledger.exchange
import kosh.libs.ledger.ledgerAPDU
import kosh.libs.ledger.toInt
import okio.Buffer
import okio.BufferedSink
import okio.ByteString
import okio.ByteString.Companion.decodeHex
import okio.ByteString.Companion.encodeUtf8
import okio.ByteString.Companion.toByteString

suspend fun LedgerManager.Connection.signTypedMessage(
    derivationPath: List<UInt>,
    eip712: Eip712V2,
    parameters: SignTypedMessageParameters?,
): ByteString {

    eip712.types.values.sortedBy { it.name }.forEach { tuple ->
        exchange(structName(tuple.name!!)) { sw ->
            sw.expectToBe(StatusWord.OK)
        }

        tuple.forEach { param ->
            exchange(structField(param.name!!, param.type)) { sw ->
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
        type = eip712.types.getValue("EIP712Domain"),
        value = eip712.domain.toTuple(),
        filters = null
    )

    if (parameters?.filters != null) {
        exchange(filterMessageInfo(parameters.filters)) { sw ->
            sw.expectToBe(StatusWord.OK)
        }
    }

    sendMessage(
        type = eip712.types.getValue(eip712.primaryType),
        value = eip712.message,
        filters = parameters?.filters,
    )

    val response = exchange(ledgerAPDU(0xe0, 0x0c, 0x00, 0x01) {
        writeByte(derivationPath.size)
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
    type: Type.Tuple,
    value: Value.Tuple,
    filters: Eip712Filters?,
) {
    sendRoot(type.name!!)
    send(type, value, filters, listOf())
}

private fun structName(name: String) = ledgerAPDU(0xe0, 0x1a, 0x00, 0x00) {
    writeUtf8(name)
}

private fun structField(name: String, type: Type) = ledgerAPDU(0xe0, 0x1a, 0x00, 0xff) {
    writeByte(type.desc() or type.key())
    if (type is Type.Tuple) {
        writeByte(type.name!!.length)
        writeUtf8(type.name!!)
    }
    type.typeSize()?.let {
        writeByte(it)
    }
    type.arrayLevelCount()?.let {
        writeByte(it)
    }
    type.arrayLevels(this)
    writeByte(name.length)
    writeUtf8(name)
}

private suspend fun LedgerManager.Connection.sendRoot(name: String) {
    exchange(ledgerAPDU(0xe0, 0x1c, 0x00, 0x00) {
        writeUtf8(name)
    }) { sw ->
        sw.expectToBe(StatusWord.OK)
    }
}

private suspend fun LedgerManager.Connection.sendArray(size: Int) {
    exchange(ledgerAPDU(0xe0, 0x1c, 0x00, 0x0f) {
        writeByte(size)
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
        writeShort(bytes.size)
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
    writeByte(eip712Filter.contractName.label.length)
    writeUtf8(eip712Filter.contractName.label)
    writeByte(eip712Filter.fields.size)
    val signature = eip712Filter.contractName.signature.decodeHex()
    writeByte(signature.size)
    write(signature)
}

private fun filterRaw(
    filter: Eip712Filters.Filter,
) = ledgerAPDU(0xe0, 0x1e, 0x00, 0xff) {
    writeByte(filter.label.length)
    writeUtf8(filter.label)
    val signature = filter.signature.decodeHex()
    writeByte(signature.size)
    write(signature)
}

private fun filterDateTime(
    filter: Eip712Filters.Filter,
) = ledgerAPDU(0xe0, 0x1e, 0x00, 0xfc) {
    writeByte(filter.label.length)
    writeUtf8(filter.label)
    val signature = filter.signature.decodeHex()
    writeByte(signature.size)
    write(signature)
}

private fun filterToken(
    filter: Eip712Filters.Filter,
    tokenIndex: Int,
) = ledgerAPDU(0xe0, 0x1e, 0x00, 0xfd) {
    writeByte(tokenIndex)
    val signature = filter.signature.decodeHex()
    writeByte(signature.size)
    write(signature)
}

private fun filterAmount(
    filter: Eip712Filters.Filter,
    tokenIndex: Int,
) = ledgerAPDU(0xe0, 0x1e, 0x00, 0xfe) {
    writeByte(filter.label.length)
    writeUtf8(filter.label)
    writeByte(tokenIndex)
    val signature = filter.signature.decodeHex()
    writeByte(signature.size)
    write(signature)
}

private fun Type.desc(): Int = when (this) {
    is Type.FixedBytes -> 1 shl 6
    is Type.Int -> 1 shl 6
    is Type.UInt -> 1 shl 6
    is Type.DynamicArray -> type.desc() or (1 shl 7)
    is Type.FixedArray -> type.desc() or (1 shl 7)
    Type.Address -> 0
    Type.Bool -> 0
    Type.DynamicBytes -> 0
    Type.DynamicString -> 0
    is Type.Tuple -> 0
    Type.Function -> error("Function type not supported by Ledger")
}

private fun Type.key(): Int = when (this) {
    is Type.Tuple -> 0
    is Type.Int -> 1
    is Type.UInt -> 2
    Type.Address -> 3
    Type.Bool -> 4
    Type.DynamicString -> 5
    is Type.FixedBytes -> 6
    Type.DynamicBytes -> 7
    is Type.DynamicArray -> type.key()
    is Type.FixedArray -> type.key()
    Type.Function -> error("Function type not supported by Ledger")
}

private fun Type.typeSize(): Int? = when (this) {
    is Type.Int -> bitSize.toInt() / 8
    is Type.UInt -> bitSize.toInt() / 8
    is Type.FixedBytes -> byteSize.toInt()
    is Type.Tuple -> null
    Type.Address -> null
    Type.Bool -> null
    Type.DynamicString -> null
    Type.DynamicBytes -> null
    is Type.DynamicArray -> type.typeSize()
    is Type.FixedArray -> type.typeSize()
    Type.Function -> error("Function type not supported by Ledger")
}

private fun Type.arrayLevelCount(): Int? = when (this) {
    is Type.Int -> null
    is Type.UInt -> null
    is Type.FixedBytes -> null
    is Type.Tuple -> null
    Type.Address -> null
    Type.Bool -> null
    Type.DynamicString -> null
    Type.DynamicBytes -> null
    is Type.DynamicArray -> 1 + (type.arrayLevelCount() ?: 0)
    is Type.FixedArray -> 1 + (type.arrayLevelCount() ?: 0)
    Type.Function -> error("Function type not supported by Ledger")
}

private fun Type.arrayLevels(sink: BufferedSink): Unit = when (this) {
    is Type.Int -> Unit
    is Type.UInt -> Unit
    is Type.FixedBytes -> Unit
    is Type.Tuple -> Unit
    Type.Address -> Unit
    Type.Bool -> Unit
    Type.DynamicString -> Unit
    Type.DynamicBytes -> Unit
    is Type.DynamicArray -> {
        type.arrayLevels(sink)
        sink.writeByte(0)
        Unit
    }

    is Type.FixedArray -> {
        type.arrayLevels(sink)
        sink.writeByte(1)
        sink.writeByte(size.toInt())
        Unit
    }

    Type.Function -> error("Function type not supported by Ledger")
}

private suspend fun LedgerManager.Connection.send(
    type: Type,
    value: Value,
    filters: Eip712Filters?,
    path: List<String>,
): Unit = when (type) {
    is Type.UInt -> (value as Value.BigNumber).value
        .toByteArray().toByteString().let { sendField(it, filters, path) }

    is Type.Int -> (value as Value.BigNumber).value
        .toTwosComplementByteArray().toByteString().let { sendField(it, filters, path) }

    is Type.Bool -> (value as Value.Bool).value.toInt().toBigInteger()
        .toByteArray().toByteString().let { sendField(it, filters, path) }

    is Type.FixedBytes -> sendField((value as Value.Bytes).value, filters, path)
    is Type.Address -> sendField((value as Value.Address).value, filters, path)
    is Type.DynamicBytes -> sendField((value as Value.Bytes).value, filters, path)
    is Type.DynamicString -> sendField((value as Value.String).value.encodeUtf8(), filters, path)

    is Type.FixedArray -> {
        sendArray(type.size.toInt())

        (value as Value.Array<*>).forEach { arrayValue ->
            send(type.type, arrayValue, filters, path + "[]")
        }
    }

    is Type.DynamicArray -> (value as Value.Array<*>).forEach { arrayValue ->
        send(type.type, arrayValue, filters, path + "[]")
    }

    is Type.Tuple -> type.zip(value as Value.Tuple).forEach { (param, paramValue) ->
        send(param.type, paramValue, filters, path + param.name!!)
    }

    is Type.Function -> error("Function type not supported by Ledger")
}

fun Type.valueAt(value: Value, path: List<String>): Value = when (this) {
    Type.Bool -> (value as Value.Bool).also { require(path.isEmpty()) }
    Type.Address -> (value as Value.Address).also { require(path.isEmpty()) }
    Type.DynamicBytes -> (value as Value.Bytes).also { require(path.isEmpty()) }
    Type.DynamicString -> (value as Value.String).also { require(path.isEmpty()) }
    is Type.FixedBytes -> (value as Value.Bytes).also { require(path.isEmpty()) }
    Type.Function -> (value as Value.Function).also { require(path.isEmpty()) }
    is Type.Int -> (value as Value.BigNumber).also { require(path.isEmpty()) }
    is Type.UInt -> (value as Value.BigNumber).also { require(path.isEmpty()) }

    is Type.Tuple -> {
        val index = indexOfFirst { it.name == path.first() }
        get(index).type.valueAt((value as Value.Tuple)[index], path.drop(1))
    }

    is Type.DynamicArray -> {
        val index = path.first().toInt()
        type.valueAt((value as Value.Tuple)[index], path.drop(1))
    }

    is Type.FixedArray -> {
        val index = path.first().toInt()
        type.valueAt((value as Value.Tuple)[index], path.drop(1))
    }
}
