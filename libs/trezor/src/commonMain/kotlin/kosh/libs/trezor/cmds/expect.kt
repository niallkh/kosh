package kosh.libs.trezor.cmds

import com.squareup.wire.Message

internal inline fun <reified T : Message<*, *>> Message<*, *>.expect(): T =
    this as? T ?: unexpectedError<T>()

internal inline fun <reified T : Message<*, *>> Message<*, *>.unexpectedError(): Nothing {
    error("Unexpected response: ${this::class.simpleName}, expected: ${T::class.simpleName}")
}

internal inline fun <reified T : Message<*, *>> Message<*, *>.expectOrNull(): T? =
    this as? T

